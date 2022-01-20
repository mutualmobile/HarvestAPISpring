package com.mutualmobile.praxisspringboot.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.*
import java.util.function.Function

@Component
class JwtTokenUtil {
    @Value("\${jwt.token.expiration.in.ms}")
    private val expirationMS: Long? = null

    @Value("\${jwt.signing.key.secret}")
    private val secret: String? = null

    //retrieve username from jwt token
    fun getUserIdFromToken(token: String?): String? {
        return getClaimFromToken(token) { obj: Claims -> obj.subject }
    }

    //retrieve expiration date from jwt token
    fun getExpirationDateFromToken(token: String?): Date? {
        return getClaimFromToken(token) { obj: Claims -> obj.expiration }
    }

    fun <T> getClaimFromToken(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    //for retrieveing any information from token we will need the secret key
    fun getAllClaimsFromToken(token: String?): Claims {
        return Jwts.parserBuilder().setAllowedClockSkewSeconds(360000000)
            .setSigningKey(secretKey()).build().parseClaimsJws(token).body
    }

    //check if the token has expired
    fun isTokenExpired(token: String?): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration?.before(Date()) == true
    }

    //generate token for user
    fun generateJWTToken(userid: String): String {
        val claims: Map<String?, Any?> = hashMapOf()
        return doGenerateToken(claims, userid)
    }

    private fun doGenerateToken(claims: Map<String?, Any?>, subject: String): String {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(Instant.now().toEpochMilli()))
            .setExpiration(Date(Instant.now().plusMillis(expirationMS!!).toEpochMilli()))
            .signWith(SignatureAlgorithm.HS512, secretKey()).compact()
    }

    //validate token
    fun validateToken(token: String?): Boolean {
        return !isTokenExpired(token)
    }

    private fun secretKey(): String {
        val encoder = Base64.getEncoder()
        var encodedText: String? = ""
        val textByte: ByteArray = secret!!.toByteArray(StandardCharsets.UTF_8)
        encodedText = encoder.encodeToString(textByte)
        return encodedText
    }
}