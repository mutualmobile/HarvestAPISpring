package com.mutualmobile.praxisspringboot.repositories

import com.mutualmobile.praxisspringboot.entities.user.DBFcmToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface FCMRepository : JpaRepository<DBFcmToken, String> {
    @Query("select token from DBFcmToken token where token.userId in :ids")
    fun findAllByUserIds(@Param("ids") list: List<String>): List<DBFcmToken>

    fun findByToken(token: String): DBFcmToken?

    @Modifying
    fun deleteByToken(token: String)
}