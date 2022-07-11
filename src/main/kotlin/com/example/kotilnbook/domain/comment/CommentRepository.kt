package com.example.kotilnbook.domain.comment

import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long>, CommentCustomRepository


interface CommentCustomRepository{

}


class CommentCustomRepositoryImpl(
        private val queryFactory: SpringDataQueryFactory
):CommentCustomRepository{





}
