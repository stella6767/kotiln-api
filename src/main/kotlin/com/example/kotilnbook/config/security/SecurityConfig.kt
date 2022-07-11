package com.example.kotilnbook.config.security

import com.example.kotilnbook.utils.logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig {

    val log = logger()

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer { web: WebSecurity -> web.ignoring().antMatchers("/*") }
    }


    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http
                .cors().configurationSource(corsConfigurationSource())

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


}