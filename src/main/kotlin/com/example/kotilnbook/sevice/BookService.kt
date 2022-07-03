package com.example.kotilnbook.sevice

import com.example.kotilnbook.domain.Book
import com.example.kotilnbook.domain.BookRepository
import com.example.kotilnbook.domain.BookResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class BookService(
   private val bookRepository: BookRepository
) {


    @Transactional(readOnly = true)
    fun findAll(): List<BookResponse> {
        return bookRepository.findAll().map { it.toDto() }
    }


}