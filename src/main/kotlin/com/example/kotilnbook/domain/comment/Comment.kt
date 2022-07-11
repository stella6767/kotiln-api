package com.example.kotilnbook.domain.comment

import com.example.kotilnbook.domain.AuditingEntity
import com.example.kotilnbook.domain.post.Post
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*


@Entity
@Table(name = "Comment")
class Comment(

) : AuditingEntity() {

    @Column(name = "content", nullable = false)
    lateinit var content: String
        protected set

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post::class)
    @JoinColumn(name = "post_id", nullable = false)
    lateinit var post: Post
        protected set

    override fun toString(): String {
        return "Comment(content='$content'"
    }


}