package com.example.kotilnbook.domain.member


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