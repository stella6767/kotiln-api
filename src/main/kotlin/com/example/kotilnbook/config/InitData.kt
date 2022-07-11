package com.example.kotilnbook.config

import com.example.kotilnbook.domain.book.Book
import com.example.kotilnbook.domain.book.BookRepository
import com.example.kotilnbook.domain.member.Member
import com.example.kotilnbook.domain.member.MemberRepository
import com.example.kotilnbook.utils.logger
import io.github.serpro69.kfaker.faker
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener


@Configuration
class InitData(
        private val bookRepository: BookRepository,
        private val memberRepository: MemberRepository
) {

    private val logger = this.logger()

    val faker = faker { }

    @EventListener(ApplicationReadyEvent::class)
    internal fun init() {
        logger.info("springboot ready")
        insertInitData()
    }


    internal fun insertInitData() {
        val books = generateBooks()
        val generateMembers = generateMembers()
        insertDummyMembers(generateMembers)
    }

    private fun insertDummyMembers(generateMembers: List<Member>) {
        for (member in generateMembers) {
            memberRepository.save(member)
        }
    }

    private fun generateBooks(): List<Book> {
        val books = listOf<Book>(
                generateBook(), generateBook(), generateBook(), generateBook(), generateBook(), generateBook()
        )

        return books
    }


    private fun generateMembers(): List<Member> {
        val members = mutableListOf<Member>()
        for (i in 1..100){
            val member = generateMember()
            members.add(member)
        }
        return members
    }


    private fun generateBook() = Book(title = faker.name.name(),
            price = 100)


    private fun generateMember() = Member(email = faker.internet.safeEmail(),
            password = "1234")


}