package com.mutualmobile.praxisspringboot.repositories

import com.mutualmobile.praxisspringboot.entities.user.DBRefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import java.time.Instant

interface RefreshTokenRepository : JpaRepository<DBRefreshToken?, String?> {
    fun findByToken(token: String?): DBRefreshToken?

    fun deleteRefreshTokenByExpiryDateIsLessThan(requestedDate: Instant): List<DBRefreshToken?>?

    @Modifying
    fun deleteByUserid(praxisUser: String?): Int
}