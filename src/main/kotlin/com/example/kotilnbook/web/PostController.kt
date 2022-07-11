package com.example.kotilnbook.web

import com.example.kotilnbook.sevice.PostSaveReq
import com.example.kotilnbook.sevice.PostService
import com.example.kotilnbook.utils.value.CmResDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class PostController(
        private val postService: PostService
) {


    @PostMapping("/post")
    fun save(@Valid postSaveReq: PostSaveReq): CmResDto {
        return CmResDto(HttpStatus.OK, "save post", postService.save(postSaveReq))
    }

}