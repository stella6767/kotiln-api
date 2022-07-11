package com.example.kotilnbook.sevice

import com.example.kotilnbook.domain.member.Member
import com.example.kotilnbook.domain.post.Post
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


data class PostResponse(
        val title: String = "",
        val content: String? = "",
        val createAt: LocalDateTime,
) {

}


data class PostSaveReq(
        @NotNull(message = "require memberId")
        val memberId: Long,

        @NotNull(message = "require title")
        val title: String,

        val content: String? = null,
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


