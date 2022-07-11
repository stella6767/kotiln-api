package com.example.kotilnbook.domain.post

import com.example.kotilnbook.domain.AuditingEntity
import com.example.kotilnbook.domain.comment.Comment
import com.example.kotilnbook.domain.member.Member
import com.example.kotilnbook.sevice.PostResponse
import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*


@Entity
@Table(name = "Post")
class Post(
    title:String,
    content:String,
    member: Member?
) : AuditingEntity() {


    @Column(name = "title", nullable = false)
    var title:String = title
        protected set

    @Column(name = "content", nullable = false)
    var content:String = content
        protected set

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member? = member
        protected set


    fun toDto():PostResponse {
        return PostResponse(
                title = this.title,
                content = this.content,
                createAt = this.createdAt
        )
    }


    override fun toString(): String {
        return "Post(title='$title', content='$content')"
    }
}

