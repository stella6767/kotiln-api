package com.example.kotilnbook.config.security

import com.example.kotilnbook.utils.logger
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    val log = logger()

    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        log.info("권한이 없다..")
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
    }
}
