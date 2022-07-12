package com.example.kotilnbook.config.security

import com.example.kotilnbook.utils.logger
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomSuccessHandler : AuthenticationSuccessHandler {

    val log = logger()

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        log.info("Login Success")
    }
}
