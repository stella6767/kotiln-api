package com.example.kotilnbook.config.security

import com.example.kotilnbook.utils.logger
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomFailureHandler : AuthenticationFailureHandler {

    val log = logger()

    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        log.info("로그인 실패..")
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "로그인 실패")
    }
}