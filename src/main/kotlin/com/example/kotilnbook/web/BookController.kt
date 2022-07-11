package com.example.kotilnbook.web

import com.example.kotilnbook.sevice.BookService
import com.example.kotilnbook.utils.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class BookController(
        private val bookService: BookService,
) {
    private val logger = logger()
    @GetMapping("/books")
    fun getBooks() = bookService.findAll()



}