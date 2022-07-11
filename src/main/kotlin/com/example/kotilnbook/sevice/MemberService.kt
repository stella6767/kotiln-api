package com.example.kotilnbook.sevice

import com.example.kotilnbook.domain.member.Member
import com.example.kotilnbook.domain.member.MemberRepository
import com.example.kotilnbook.utils.logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Service
class MemberService(
        private val memberRepository: MemberRepository
) {

    val log = logger()

    fun findAll(pageable: Pageable): Page<Member> {
        return memberRepository.findAllByPage(pageable)
    }


}