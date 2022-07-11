package com.example.kotilnbook.sevice

import com.example.kotilnbook.domain.comment.Comment
import com.example.kotilnbook.domain.comment.CommentRepository
import com.example.kotilnbook.utils.logger
import org.springframework.stereotype.Service


@Service
class CommentService(
    private val commentRepository: CommentRepository
) {

    val log = logger()


    internal fun findAll(): MutableList<Comment> {
        return commentRepository.findAll()
    }


}