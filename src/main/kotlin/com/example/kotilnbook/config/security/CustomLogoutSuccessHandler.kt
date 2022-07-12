package com.example.kotilnbook.config.security

import com.example.kotilnbook.utils.logger
import com.example.kotilnbook.utils.responseData
import com.example.kotilnbook.utils.value.CmResDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomLogoutSuccessHandler(
        private val om: ObjectMapper
) : LogoutSuccessHandler {

    val log = logger()

    override fun onLogoutSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        log.info("Logout Success")
        val context = SecurityContextHolder.getContext()
        context.authentication = null
        SecurityContextHolder.clearContext()
        val cmRespDto: CmResDto<*> = CmResDto<Any>(HttpStatus.OK, "로그아웃되었습니다.", null)
        responseData(response, om.writeValueAsString(cmRespDto))
    }
}