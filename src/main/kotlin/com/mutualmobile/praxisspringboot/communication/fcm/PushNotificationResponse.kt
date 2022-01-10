package com.mutualmobile.praxisspringboot.communication.fcm

data class PushNotificationResponse(
    var status: Int = 0,
    var message: String? = null
)