package com.example.kotilnbook.sevice

import java.time.LocalDateTime


data class PostResponse(
        val title: String = "",
        val content: String = "",
        val createAt: LocalDateTime,
) {


}



