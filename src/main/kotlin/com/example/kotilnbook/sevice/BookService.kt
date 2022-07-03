package com.example.kotilnbook.sevice

import com.example.kotilnbook.domain.Book
import com.example.kotilnbook.domain.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class BookService(
   private val bookRepository: BookRepository
) {


    @Transactional
    fun findAll():List<Book>{
        return bookRepository.findAll()
    }


}