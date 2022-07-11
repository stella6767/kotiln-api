package com.example.kotilnbook.domain.member

import com.example.kotilnbook.domain.AuditingEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "member")
class Member(
        email: String,
        password: String,
        role: Role = Role.USER
) : AuditingEntity() {

    @Column(name = "email", nullable = false)
    var email = email
        protected set  //이렇게 지저분하게 private 도 못하고, 생성자에도 추가해줘야 되고.. 방법이 없나..

    @Column(name = "password", nullable = false)
    var password: String = password
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: Role = role
        protected set



    fun toDto():MemberRes{
       return MemberRes(
                id = this.id,
                email = this.email,
                password = this.password,
                role = this.role,
                createAt =this.createdAt
        )

    }



    companion object Factory {
        fun createDefaultMember(memberId: Long): Member {
            val member = Member("", "")
            member.id = memberId
            return member
        }
    }

    override fun toString(): String {
        return "Member(email='$email', password='$password', role=$role)"
    }

}



enum class Role {
    USER,
    ADMIN
}