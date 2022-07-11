package com.example.kotilnbook.domain.comment

import com.example.kotilnbook.domain.AuditingEntity
import com.example.kotilnbook.domain.post.Post
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*


@Entity
@Table(name = "Comment")
class Comment(
    content:String,
    post: Post
) : AuditingEntity() {

    @Column(name = "content", nullable = false)
    var content: String = content
        protected set

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post::class)
    @JoinColumn(name = "post_id", nullable = false)
    var post: Post = post
        protected set

    override fun toString(): String {
        return "Comment(content='$content'"
    }


}