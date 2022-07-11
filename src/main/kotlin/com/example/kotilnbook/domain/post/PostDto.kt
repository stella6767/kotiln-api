package com.example.kotilnbook.sevice

import com.example.kotilnbook.domain.member.Member
import com.example.kotilnbook.domain.post.Post
import java.time.LocalDateTime


data class PostResponse(
        val title: String = "",
        val content: String = "",
        val createAt: LocalDateTime,
) {

}


data class PostSaveReq(
        val memberId: Long,
        val title: String,
        val content: String,
) {

    fun toEntity(): Post {
        val member = Member.Factory.createDefaultMember(memberId)
        return Post(
                title = this.title,
                content = this.content,
                member = member
        )
    }

}


