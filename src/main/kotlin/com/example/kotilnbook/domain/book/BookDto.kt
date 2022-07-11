package com.example.kotilnbook.domain.book

import java.time.LocalDateTime

data class BookResponse(
    val title: String? = null,
    val price: Int = 0,
    val createAt: LocalDateTime? = null,
) {

}
