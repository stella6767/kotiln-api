package com.example.kotilnbook.utils

import java.io.PrintWriter
import javax.servlet.http.HttpServletResponse


fun responseData(resp: HttpServletResponse, jsonData: String?) {
    val out: PrintWriter
    println("응답 데이터: $jsonData")
    resp.setHeader("Content-Type", "application/json; charset=utf-8")

    try {
        out = resp.getWriter()
        out.println(jsonData)
        out.flush() //버퍼 비우기
    } catch (e: Exception) {
        e.printStackTrace()
    }
}