package com.mutualmobile.praxisspringboot.security

import com.mutualmobile.praxisspringboot.exceptions.TokenRefreshException
import com.mutualmobile.praxisspringboot.entities.user.DBRefreshToken
import com.mutualmobile.praxisspringboot.repositories.RefreshTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.concurrent.TimeUnit

@Service
class RefreshTokenService {

    private val logger = LoggerFactory.getLogger(RefreshTokenService::class.java)

    @Value("\${jwt.refreshtoken.expiration.in.ms}")
    private val refreshTokenDurationMs: Long? = null

    @Autowired
    lateinit var refreshTokenRepository: RefreshTokenRepository

    fun findByToken(token: String?): DBRefreshToken? {
        return refreshTokenRepository.findByToken(token)
    }

    fun createRefreshToken(id: String): DBRefreshToken {
        var refreshToken = DBRefreshToken()
        refreshToken.userid = id
        refreshToken.expiryDate = Instant.now().plusMillis(refreshTokenDurationMs!!)
        refreshToken = refreshTokenRepository.save(refreshToken)
        return refreshToken
    }

    fun verifyExpiration(token: DBRefreshToken): DBRefreshToken {
        if (token.expiryDate!! < Instant.now()) {
            refreshTokenRepository.delete(token)
            throw TokenRefreshException(token.token.toString(), "Refresh token was expired. Please make a new signin request")
        }
        return token
    }

    @Transactional
    fun deleteByUserId(userId: String): Int {
        return refreshTokenRepository.deleteByUserid(userId)
    }

    @Transactional
    fun deleteObsoleteTokens() {
        val date = Instant.now().minusMillis(TimeUnit.DAYS.toMillis(2))
        val tokens = refreshTokenRepository.deleteRefreshTokenByExpiryDateIsLessThan(date)
        logger.info("deleted obsolete tokens ids $tokens")
    }
}