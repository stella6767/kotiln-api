package com.example.kotilnbook.domain.member

import com.example.kotilnbook.domain.book.Book
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils

interface MemberRepository : JpaRepository<Member, Long>, MemberCustomRepository {

}


interface MemberCustomRepository {
    fun findById(id: Long): Book
    fun findAllByPage(pageable: Pageable): Page<Member>
    fun findMemberByEmail(email: String?): Member
}

class MemberCustomRepositoryImpl(
        private val queryFactory: SpringDataQueryFactory
) : MemberCustomRepository {
    override fun findById(id: Long): Book {


        TODO("Not yet implemented")
    }

    override fun findAllByPage(pageable: Pageable): Page<Member> {

        val members = queryFactory.listQuery<Member> {
            select(entity(Member::class))
            limit(pageable.pageSize)
            offset(pageable.offset.toInt())
            from(entity(Member::class))
        }

        val countQuery = queryFactory.listQuery<Member> {
            select(entity(Member::class))
            from(entity(Member::class))
        }


        return PageableExecutionUtils.getPage(members, pageable) {
            countQuery.size.toLong()
        }
    }

    override fun findMemberByEmail(email: String?): Member {

        return queryFactory.singleQuery<Member> {
            select(entity(Member::class))
            from(entity((Member::class)))
        }

    }
}