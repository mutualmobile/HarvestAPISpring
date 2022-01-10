package com.mutualmobile.praxisspringboot.entities.notification

import com.mutualmobile.praxisspringboot.data.models.notification.NotificationStatus
import com.mutualmobile.praxisspringboot.data.models.notification.NotificationType
import com.mutualmobile.praxisspringboot.entities.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "notifications")
data class DBNotification(
    var body: String,
    @Enumerated(EnumType.ORDINAL)
    var notificationType: NotificationType,
    var userId: String?,
    var payload: String,
    @Enumerated(EnumType.ORDINAL)
    var status: NotificationStatus = NotificationStatus.UNREAD,
    var title: String = ""
) : BaseEntity()