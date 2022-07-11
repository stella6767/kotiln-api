package com.example.kotilnbook.web

import com.example.kotilnbook.sevice.MemberService
import com.example.kotilnbook.utils.value.CmResDto
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService
) {
    @GetMapping("/members")
    fun findAll(@PageableDefault(size = 10) pageable:Pageable): CmResDto {
        return CmResDto(1, "find Members",  memberService.findAll(pageable))
    }





}