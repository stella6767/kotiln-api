package com.example.kotilnbook.config.security

import com.example.kotilnbook.utils.logger
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAccessDeniedHandler : AccessDeniedHandler {

    val log = logger()

    override fun handle(request: HttpServletRequest, response: HttpServletResponse, accessDeniedException: AccessDeniedException) {
        log.info("403 떤다.")
        response.sendError(HttpServletResponse.SC_FORBIDDEN)
    }
}
