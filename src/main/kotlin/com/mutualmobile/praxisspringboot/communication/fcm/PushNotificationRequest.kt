package com.mutualmobile.praxisspringboot.communication.fcm

import com.mutualmobile.praxisspringboot.entities.user.DBFcmToken


data class PushNotificationRequest(
    var title: String? = null,
    var message: String? = null,
    var topic: String? = null,
    var token: DBFcmToken? = null,
    var data: Map<String, String>? = null
)