package com.example.kotilnbook.domain.member

import java.time.LocalDateTime


data class MemberRes(
        val id:Long?,
        val email: String,
        val password: String,
        val role: Role,
        val createAt: LocalDateTime
)

data class LoginDto (
        val email: String,
        val password: String,
        ) {
    fun toEntity(encPassword: String): Member {
        return Member(
                email = this.email,
                password = encPassword,
                role = Role.USER
        )
    }
}


data class JoinReq(
        val email: String,
        val password: String,

        ) {
    fun toEntity(encPassword: String): Member {
        return Member(
                email = this.email,
                password = encPassword,
                role = Role.USER
        )
    }
}