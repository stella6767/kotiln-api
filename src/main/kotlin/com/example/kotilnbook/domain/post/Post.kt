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

) : AuditingEntity() {


    @Column(name = "title", nullable = false)
    lateinit var title:String
        protected set

    @Column(name = "content", nullable = false)
    lateinit var content:String
        protected set

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class)
    @JoinColumn(name = "member_id", nullable = false)
    lateinit var member: Member
        protected set


    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    var comments: MutableList<Comment> = mutableListOf()
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

