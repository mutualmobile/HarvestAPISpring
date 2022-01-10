package com.mutualmobile.praxisspringboot.services.notifications

import com.mutualmobile.praxisspringboot.controllers.authuser.getToken
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.notification.NotificationModel
import com.mutualmobile.praxisspringboot.data.models.notification.NotificationStatus
import com.mutualmobile.praxisspringboot.entities.notification.DBNotification
import com.mutualmobile.praxisspringboot.security.JwtTokenUtil
import com.mutualmobile.praxisspringboot.repositories.NotificationsRepository
import com.mutualmobile.praxisspringboot.services.user.UserDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.math.BigInteger
import javax.servlet.http.HttpServletRequest


@Service
class NotificationServiceImpl : NotificationService {

    @Autowired
    lateinit var notificationsRepository: NotificationsRepository

    @Autowired
    lateinit var userDataService: UserDataService

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    override fun getNotifications(
        offset: Int?,
        limit: Int?,
        request: HttpServletRequest,
    ): ResponseEntity<ApiResponse<List<NotificationModel<Any>>>> {
        val userId = jwtTokenUtil.getUserIdFromToken(request.getToken())
        val safeOffset = offset ?: 0
        val safeLimit = limit ?: 10
        val order: Sort.Order = Sort.Order(Sort.Direction.DESC, "createdTime")

        val notification =
            notificationsRepository.findAllByUserId(
                userId!!, PageRequest.of(
                    safeOffset, safeLimit, Sort.by(order)
                )
            )
        if (notification.content.isEmpty()) {
            return ResponseEntity.ok(ApiResponse("No Notifications found!"));
        }
        val records = notification.content.map {
            it.toNotificationModel()
        }
        return ResponseEntity.ok(ApiResponse("Success!", data = records));
    }

    override fun readNotification(notificationId: String) {
        val notification = notificationsRepository.findById(notificationId).orElse(null)
        notification?.let {
            it.status = NotificationStatus.READ
            notificationsRepository.save(notification)
        }
    }

    override fun getUnreadNotificationCount(request: HttpServletRequest): ResponseEntity<ApiResponse<Long>> {
        val userId = jwtTokenUtil.getUserIdFromToken(request.getToken())
        val count = notificationsRepository.getUnReadNotificationCount(userId!!)
        count.firstOrNull()?.let {
            it.firstOrNull()?.let {
                return ResponseEntity.ok(ApiResponse("Success!", data = (it as BigInteger).toLong()))
            }
        }
        return ResponseEntity.ok(ApiResponse("Success!", data = 0))
    }

    fun <T> Array<T>.firstString(): String? {
        return (this[0] as? String)
    }
}

private fun DBNotification.toNotificationModel(): NotificationModel<Any> {
    return NotificationModel(
        payload = this.payload,
        id = this.id,
        body = this.body,
        notificationType = this.notificationType,
        postedTime = this.createdTime,
        userId = this.userId,
        status = this.status
    )
}
