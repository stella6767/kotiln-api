package com.example.kotilnbook.sevice

import com.example.kotilnbook.config.security.PrincipalDetails
import com.example.kotilnbook.domain.member.JoinReq
import com.example.kotilnbook.domain.member.Member
import com.example.kotilnbook.domain.member.MemberRepository
import com.example.kotilnbook.utils.logger
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserDetailsService {

    val log = logger()

    override fun loadUserByUsername(email: String): UserDetails {
        log.info("/login 이 호출되면 자동 실행되어 username 이 DB에 있는지 확인한다.")
        val principal: Member? = memberRepository.findMemberByEmail(email)
        //session.setAttribute("principal",principal); - jsp 아니라면 세션에 저장하고 사용해야된다.
        //session.setAttribute("principal",principal); - jsp 아니라면 세션에 저장하고 사용해야된다.
        return PrincipalDetails(principal)
    }


    @Transactional
    fun joinMember(dto: JoinReq) {
        //여기서 trim 검사말고 프론트쪽에서 검사하는 게 나을듯.
        val memberEntity: Member? = memberRepository.findMemberByEmail(dto.email)
        log.info("memberEntity $memberEntity")
        if (memberEntity != null) throw IllegalArgumentException("duplicated email already exist")
        val encPassword = bCryptPasswordEncoder.encode(dto.password)
        memberRepository.save(dto.toEntity(encPassword))

    }
}