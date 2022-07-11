package com.example.kotilnbook.config.aop

import com.example.kotilnbook.utils.logger
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

/**
 * 전통적인 프록시 기반 자바 스타일 AOP
 */


@Component
@Aspect ///AOP 설정 클래스
class LoggerAspect {

    val log = logger()

    @Pointcut("execution(* com.example.kotilnbook.*..*Controller.*(..))")
    private fun controllerCut() {
    }

    @Before("controllerCut()")
    fun requestLoggerAdvice(joinPoint: JoinPoint) {
        val type = joinPoint.signature.declaringTypeName
        val method = joinPoint.signature.name
        val args = joinPoint.args
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        log.info("""
                 requset url : ${request.servletPath}
                 type : $type
                 method : $method
                        """)
        if (args.size <= 0) log.info("no parameter")
        for (arg in args) {
            //log.info("parameter type = {}", arg.getClass().getSimpleName());
            log.info("parameter value = {}", arg)
        }
    }

    @AfterReturning(pointcut = "controllerCut()", returning = "result")
    fun logAfter(joinPoint: JoinPoint, result: Any) {
        //interceptor를 이용할까 하다가, 그냥 pontcut 적용
        log.info(joinPoint.signature.name + ",   Method Return value : " + result)
    }

    @AfterThrowing(pointcut = "controllerCut()", throwing = "exception")
    fun logAfterThrowing(joinPoint: JoinPoint, exception: Throwable) {
        log.error("An exception has been thrown in " + joinPoint.signature.name + " ()")
        log.error("Cause : " + exception.cause)
    }
}