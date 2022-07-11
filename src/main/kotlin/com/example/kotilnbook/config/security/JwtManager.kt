package com.example.kotilnbook.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.kotilnbook.utils.logger
import org.springframework.beans.factory.annotation.Value
import java.util.*

class JwtManager(

) {

    private val log = logger()
    val tokenPrefix = "Bearer"
    val headerString = "Authorization "
    private val CLAIM_EMAIL = "email"
    private val CLAIM_PASSWORD = "password"
    private val CLAIM_ID = "id"

    @Value("\${jwt.ACCESS_TOKEN_VALIDATION_SECOND}")
    private val ACCESS_TOKEN_VALIDATION_RAW_SECOND: Long = 0

    @Value("\${jwt.SECRET_KEY}")
    private val secretKey: String = ""


    fun getMemberEmail(token: String?): String? {
        return JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token)
                .getClaim(CLAIM_EMAIL).asString()
    }

    fun getMemberId(token: String?): Long {
        return java.lang.Long.valueOf(JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token)
                .getClaim(CLAIM_ID).asString())
    }

    fun getMemberPassword(token: String?): String? {
        return JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token)
                .getClaim(CLAIM_PASSWORD).asString()
    }


    fun generateAccessToken(principalDetails: PrincipalDetails): String? {
        log.info("검증에 활용할 userId: " + principalDetails.member.id)
        return doGenerateToken(principalDetails, ACCESS_TOKEN_VALIDATION_RAW_SECOND)
    }


    fun doGenerateToken(principalDetails: PrincipalDetails, expireTime: Long): String? {
        return JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(Date(System.nanoTime() + expireTime))
                .withClaim(CLAIM_EMAIL, principalDetails.getUsername())
                .withClaim(CLAIM_PASSWORD, principalDetails.getPassword())
                .withClaim(CLAIM_ID, principalDetails.member.id)
                .sign(Algorithm.HMAC512(secretKey))
    }


    fun validateToken(token: String?, principalDetails: PrincipalDetails): Boolean? {

        return true
    }


}

