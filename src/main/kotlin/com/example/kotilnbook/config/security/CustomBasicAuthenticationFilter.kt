package com.example.kotilnbook.config.security

import com.example.kotilnbook.domain.member.Member
import com.example.kotilnbook.domain.member.MemberRepository
import com.example.kotilnbook.utils.logger
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomBasicAuthenticationFilter(
        authenticationManager: AuthenticationManager?,
        memberRepository: MemberRepository,
        jwtManager: JwtManager
) : BasicAuthenticationFilter(authenticationManager) {


    private val log = logger()
    private val memberRepository: MemberRepository
    private val jwtManager: JwtManager

    init {
        this.memberRepository = memberRepository
        this.jwtManager = jwtManager
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        log.debug("권한이나 인증이 필요한 요청이 들어옴")
        val header = request.getHeader(jwtManager.headerString)
        if (header == null || !header.startsWith(jwtManager.headerString)) {
            chain.doFilter(request, response)
            return
        }
        log.debug("Authorization는 $header")
        val token: String = request.getHeader(jwtManager.headerString)
                .replace(jwtManager.tokenPrefix, "")
        val email = jwtManager.getMemberEmail(token)
        log.debug("token은 $token")
        var principalDetails: PrincipalDetails? = null
        val memberEntity: Member? = memberRepository.findMemberByEmail(email)

        if (memberEntity != null) {

            // 1. authenticationManager.authenticate() 함수를 타게 되면 인증을 한번 더 하게 되고
            // 이때 비밀번호 검증을 하게 되는데, User 테이블에서 가져온 비밀번호 해시값으로 비교가 불가능하다.
            // 2. 그래서 강제로 세션에 저장만 한다.
            // 3. 단점은 @AuthenticationPrincipal 어노테이션을 사용하지 못하는 것이다.
            // 4. 이유는 UserDetailsService를 타지 않으면 @AuthenticationPrincipal 이 만들어지지 않는다.
            // 5. 그래서 @LoginUser 을 하나 만들려고 한다.
            // 6. 그러므로 모든 곳에서 @AuthenticationPrincipal 사용을 금지한다. @LoginUser 사용 추천!!
            principalDetails = PrincipalDetails(memberEntity)
            val authentication: Authentication = UsernamePasswordAuthenticationToken(
                    principalDetails,
                    principalDetails.password,
                    principalDetails.authorities)

            SecurityContextHolder.getContext().authentication = authentication
        }
        chain.doFilter(request, response)
    }
}