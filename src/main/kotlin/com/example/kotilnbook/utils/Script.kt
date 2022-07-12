package com.example.kotilnbook.utils

import java.io.IOException
import java.io.PrintWriter
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


fun responseData(resp: HttpServletResponse, jsonData: String?) {
    val out: PrintWriter
    println("응답 데이터: $jsonData")  //todo outside class에서 Loggging 을 어떻게 적용시키지?
    resp.setHeader("Content-Type", "application/json; charset=utf-8")

    try {
        out = resp.getWriter()
        out.println(jsonData)
        out.flush() //버퍼 비우기
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


fun printRequest(httpRequest: HttpServletRequest) {
    println(" \n\n Headers")
    val headerNames: Enumeration<*> = httpRequest.headerNames
    while (headerNames.hasMoreElements()) {
        val headerName = headerNames.nextElement() as String
        println(headerName + " = " + httpRequest.getHeader(headerName))
    }
    println("\n\n Parameters")
    val params: Enumeration<*> = httpRequest.parameterNames
    while (params.hasMoreElements()) {
        val paramName = params.nextElement() as String
        println(paramName + " = " + httpRequest.getParameter(paramName))
    }
    println("\n\n Row data")
    extractPostRequestBody(httpRequest);
    //getBody(httpRequest);
}



fun extractPostRequestBody(request: HttpServletRequest) {
    try {
        val reduce = request.reader.lines().reduce("") { obj: String, str: String -> obj + str }
        println(reduce)
    } catch (e: IOException) {
        throw RuntimeException(e)
    }
}