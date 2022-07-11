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

interface PostRepository : JpaRepository<Post, Long>, PostJDSLRepository {
}


interface PostJDSLRepository {
    fun findById(id: Long): Post
    fun save(post: Post)
    fun findAll(): List<Post>
}


@Repository
class PostJDSLRepositoryImpl(
        private val queryFactory: SpringDataQueryFactory,
        private val em: EntityManager
) : PostJDSLRepository {

    private val logger = logger()

    @PostConstruct
    fun entityManagerDiTest(){
        logger.info("why.....????? em=>{} ", em)
    }


    override fun findById(id: Long): Post {
        return queryFactory.singleQuery {
            select(entity(Post::class))
            from(entity(Post::class))
            where(col(Post::id).equal(id))
        }
    }


    override fun save(post: Post):Unit{
        em.persist(post)
    }



    override fun findAll():List<Post> {
        return queryFactory.listQuery {
            select(entity(Post::class))
            from(entity(Post::class))

        }

    }



}