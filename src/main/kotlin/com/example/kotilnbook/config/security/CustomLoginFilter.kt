package com.example.kotilnbook.config.security

import com.example.kotilnbook.domain.member.LoginDto
import com.example.kotilnbook.utils.logger
import com.example.kotilnbook.utils.responseData
import com.example.kotilnbook.utils.value.CmResDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomLoginFilter(
                        private val om: ObjectMapper,
                        private val jwtManager: JwtManager
) : UsernamePasswordAuthenticationFilter() {


    val log = logger()

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        log.info("인증 요청 옴")
        var loginDto: LoginDto? = null
        try {
            loginDto = om.readValue(request.inputStream, LoginDto::class.java) //json => java object
            log.info("로그인 dto: '{}'", loginDto)
        } catch (e: Exception) {
            log.warn("JwtLoginFilter : 로그인 요청 dto 생성 중 실패: '{}'", e.message)
        }

        //1. UsernamePassword 토큰 만들기
        val authToken = UsernamePasswordAuthenticationToken(loginDto?.email, loginDto?.password)

        //2. AuthenticationManager에게 토큰을 전달하면 -> 자동으로 UserDetailsService가 호출=> 응답 Authentication
        return authenticationManager.authenticate(authToken)
        //return 될 때 authentication객체가 session 영역에 저장됨.
        //굳이 세션을 만들 이유는 없지만, 권한 처리 때문에 session에 넣어주자.
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {

        //principalDetails 객체를 받아오고
        log.info("로그인 완료되어서 세션 만들어짐. 이제 JWT토큰 만들어서 response.header에 응답할 차리")
        val principalDetails = authResult.principal as PrincipalDetails
        val jwtToken = jwtManager.generateAccessToken(principalDetails)

        response.addHeader(jwtManager.headerString, jwtManager.tokenPrefix+ " " + jwtToken)

        val loginSuccessDto = CmResDto(HttpStatus.OK, "로그인 성공", principalDetails.member)
        responseData(response, om.writeValueAsString(loginSuccessDto))
    }


}
