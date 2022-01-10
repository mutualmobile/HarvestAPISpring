package com.mutualmobile.praxisspringboot.repositories

import com.mutualmobile.praxisspringboot.entities.notification.DBNotification
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface NotificationsRepository : JpaRepository<DBNotification, String> {
    fun findAllByUserId(userId: String, page: Pageable): Page<DBNotification>

    @Query(
        "select count(CASE WHEN status = 1 THEN 1 END) as unreadCount from notifications where user_id = :userId",
        nativeQuery = true
    )
    fun getUnReadNotificationCount(@Param("userId") userId: String): List<Array<Any>>
}