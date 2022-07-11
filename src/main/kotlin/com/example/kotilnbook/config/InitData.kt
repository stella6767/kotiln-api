package com.example.kotilnbook.config

import com.example.kotilnbook.domain.book.Book
import com.example.kotilnbook.domain.book.BookRepository
import com.example.kotilnbook.utils.logger
import io.github.serpro69.kfaker.faker
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener


@Configuration
class InitData(
        private val bookRepository: BookRepository
) {

    private val logger = this.logger()

    val faker = faker { }

    @EventListener(ApplicationReadyEvent::class)
    internal fun init() {

        logger.info("springboot ready")

        insertInitData()
    }


    internal fun insertInitData() {

        val books = listOf<Book>(
                generateBook(), generateBook(), generateBook(), generateBook(), generateBook(), generateBook()
        )

        for (book in books) {
            bookRepository.save(book)
        }
    }

    private fun generateBook() = Book(title = faker.name.name(),
            price = 100)


}