package com.example.kotilnbook.domain

import com.example.kotilnbook.utils.logger
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager


@Repository
class BookRepository(
        private val queryFactory: SpringDataQueryFactory,
        private val em:EntityManager
) {

    private val logger = logger()

    fun findById(id: Long): Book {
        return queryFactory.singleQuery {
            select(entity(Book::class))
            from(entity(Book::class))
            where(col(Book::id).equal(id))
        }
    }


    @Transactional
    fun save(book: Book):Unit{
        em.persist(book)
    }



    fun findAll():List<Book> {

        return queryFactory.listQuery {
            select(entity(Book::class))
            from(entity(Book::class))

        }

    }



}