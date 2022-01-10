package com.mutualmobile.praxisspringboot.services.notifications

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.notification.NotificationModel
import org.springframework.http.ResponseEntity
import javax.servlet.http.HttpServletRequest

interface NotificationService {
    fun getNotifications(
        offset: Int?,
        limit: Int?,
        request: HttpServletRequest,
    ): ResponseEntity<ApiResponse<List<NotificationModel<Any>>>>

    fun getUnreadNotificationCount(request: HttpServletRequest): ResponseEntity<ApiResponse<Long>>
    fun readNotification(notificationId: String)
}