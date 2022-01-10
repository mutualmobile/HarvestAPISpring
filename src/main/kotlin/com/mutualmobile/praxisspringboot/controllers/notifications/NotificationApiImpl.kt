package com.mutualmobile.praxisspringboot.controllers.notifications

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.notification.NotificationModel
import com.mutualmobile.praxisspringboot.services.notifications.NotificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class NotificationApiImpl : NotificationApi {

    @Autowired
    lateinit var notificationService: NotificationService

    override fun getUnreadNotificationCount(request: HttpServletRequest): ResponseEntity<ApiResponse<Long>> {
        return notificationService.getUnreadNotificationCount(request)
    }

    override fun getNotifications(
        offset: Int?,
        limit: Int?,
        request:HttpServletRequest
    ): ResponseEntity<ApiResponse<List<NotificationModel<Any>>>> {
        return notificationService.getNotifications(offset, limit,request)
    }
}