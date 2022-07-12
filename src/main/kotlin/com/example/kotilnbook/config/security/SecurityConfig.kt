package com.example.kotilnbook.config.security

import com.example.kotilnbook.domain.member.MemberRepository
import com.example.kotilnbook.sevice.AuthService
import com.example.kotilnbook.utils.logger
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig(
     private val memberRepository: MemberRepository,
     private val om:ObjectMapper,
     private val configuration:AuthenticationConfiguration
) {


    val log = logger()

    //@Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer { web: WebSecurity -> web.ignoring().antMatchers("/*") }
    }


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {


        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// STATELESS: session을 사용하지 않겠다는 의미
                .and()
                .addFilter(customLoginFilter())
                .addFilter(customBasicAuthenticationFilter())
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint())
                .accessDeniedHandler(jwtAccessDeniedHandler()) //권한이 없을 때
                .and()
                .authorizeRequests()
//                .antMatchers("/user/**").authenticated() //인증만 되면 ok
//                .antMatchers("/admin/**").access("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler())


        return http.build()
    }


    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val config = CorsConfiguration()
        config.allowCredentials = true //내서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정
        config.addAllowedOriginPattern("*") //모든 ip에 응답을 허용하겠다.
        config.addAllowedMethod("*") // 모든 post,get,put,delete, patch ..요청을 허용하겠다.
        config.addAllowedHeader("*") //모든 header에 응답을 허용하겠다.
        //config.addAllowedOrigin("*");
        config.addExposedHeader(jwtManager().headerString) //authorization 노출
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }



    @Bean
    fun jwtManager():JwtManager {
        return JwtManager()
    }

    @Bean
    fun encode(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun customBasicAuthenticationFilter(): CustomBasicAuthenticationFilter {
        return CustomBasicAuthenticationFilter(authenticationManager(), memberRepository , jwtManager())
    }

    @Bean
    fun authenticationManager(): AuthenticationManager? {
        return configuration.getAuthenticationManager()
    }

//    @Autowired
//    fun configure(builder: AuthenticationManagerBuilder) {
//        builder.userDetailsService<UserDetailsService>(authService).passwordEncoder(BCryptPasswordEncoder())
//    }


    @Bean
    fun customLoginFilter(): CustomLoginFilter {
        val customLoginFilter = CustomLoginFilter(om, jwtManager())
        customLoginFilter.setFilterProcessesUrl("/login")
        customLoginFilter.setAuthenticationManager(authenticationManager())
        customLoginFilter.setAuthenticationSuccessHandler(CustomSuccessHandler())
        customLoginFilter.setAuthenticationFailureHandler(CustomFailureHandler())
        return customLoginFilter
    }

    @Bean
    fun customLogoutSuccessHandler(): CustomLogoutSuccessHandler? {
        return CustomLogoutSuccessHandler(om)
    }
    @Bean
    fun jwtAccessDeniedHandler(): JwtAccessDeniedHandler? {
        return JwtAccessDeniedHandler()
    }


    @Bean
    fun jwtAuthenticationEntryPoint(): JwtAuthenticationEntryPoint? {
        return JwtAuthenticationEntryPoint()
    }




}