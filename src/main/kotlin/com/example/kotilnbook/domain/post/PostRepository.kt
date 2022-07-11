package com.example.kotilnbook.domain.post

import com.example.kotilnbook.utils.logger
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.annotation.PostConstruct
import javax.persistence.EntityManager

interface PostRepository : JpaRepository<Post, Long>, PostCustomRepository {
}


interface PostCustomRepository {
    fun findById(id: Long): Post
    fun findAll(): List<Post>
}


@Repository
class PostCustomRepositoryImpl(
        private val queryFactory: SpringDataQueryFactory,
) : PostCustomRepository {


    private val logger = logger()


    override fun findById(id: Long): Post {
        return queryFactory.singleQuery {
            select(entity(Post::class))
            from(entity(Post::class))
            where(col(Post::id).equal(id))
        }
    }


    override fun findAll():List<Post> {
        return queryFactory.listQuery {
            select(entity(Post::class))
            from(entity(Post::class))

        }

    }



}