package com.example.kotilnbook.config.security

import com.example.kotilnbook.domain.member.Member
import com.example.kotilnbook.utils.logger
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class PrincipalDetails(
        member: Member?
) : UserDetails{

    var member: Member? = member
        protected set

    val log = logger()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        log.info("Role 검증 하는 중")
        val collectors: MutableCollection<GrantedAuthority> = ArrayList()
        collectors.add(GrantedAuthority { "ROLE_" + member?.role.toString() })
        return collectors
    }


    override fun getPassword(): String? {
        return member?.password
    }

    override fun getUsername(): String? {
        return member?.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }


}