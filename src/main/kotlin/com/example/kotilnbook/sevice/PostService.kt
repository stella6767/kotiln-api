package com.example.kotilnbook.sevice

import com.example.kotilnbook.domain.book.BookResponse
import com.example.kotilnbook.domain.post.Post
import com.example.kotilnbook.domain.post.PostRepository
import com.example.kotilnbook.utils.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
        private val postRepository: PostRepository
) {

    private val logger = logger()

    @Transactional(readOnly = true)
    fun findAll(): List<PostResponse> {
        return postRepository.findAll().map { it.toDto() }
    }

    @Transactional
    fun save(postSaveReq: PostSaveReq): PostResponse {
        return postRepository.save(postSaveReq.toEntity()).toDto()
    }


}