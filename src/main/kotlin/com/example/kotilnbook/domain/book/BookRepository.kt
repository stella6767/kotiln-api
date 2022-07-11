package com.example.kotilnbook.domain.book

import com.example.kotilnbook.utils.logger
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import org.hibernate.sql.Insert
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager



interface BookRepository : JpaRepository<Book, Long>, BookCustomRepository {


}

interface BookCustomRepository {
    fun findById(id: Long): Book
    fun findAll(): List<Book>
}

class BookCustomRepositoryImpl(
        private val queryFactory: SpringDataQueryFactory,
) : BookCustomRepository {

    private val logger = logger()

    override fun findById(id: Long): Book {
        return queryFactory.singleQuery {
            select(entity(Book::class))
            from(entity(Book::class))
            where(col(Book::id).equal(id))
        }
    }

    override fun findAll(): List<Book> {
        return queryFactory.listQuery {
            select(entity(Book::class))
            from(entity(Book::class))
        }
    }



}

