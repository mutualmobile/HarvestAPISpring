package com.mutualmobile.praxisspringboot.data.models.notification

import java.util.*

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class NotificationModel<T>(
    val id: String?,
    val body: String,
    val notificationType: NotificationType?,
    val postedTime: Date?,
    val userId: String?,
    val payload: T?,
    val status: NotificationStatus
)

enum class NotificationType {
    TYPE_1,
    TYPE_2,
}

enum class NotificationStatus {
    READ,
    UNREAD,
}
