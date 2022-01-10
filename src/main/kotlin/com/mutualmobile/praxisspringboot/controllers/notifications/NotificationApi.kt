package com.mutualmobile.praxisspringboot.controllers.notifications

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.notification.NotificationModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
interface NotificationApi {

    @GetMapping(Endpoint.NOTIFICATION_COUNT)
    fun getUnreadNotificationCount(request: HttpServletRequest): ResponseEntity<ApiResponse<Long>>

    @GetMapping(Endpoint.NOTIFICATIONS)
    fun getNotifications(
        @RequestParam(value = Endpoint.Params.OFFSET, required = false) offset: Int?,
        @RequestParam(value = Endpoint.Params.LIMIT, required = false) limit: Int?,
        request: HttpServletRequest
    ): ResponseEntity<ApiResponse<List<NotificationModel<Any>>>>
}