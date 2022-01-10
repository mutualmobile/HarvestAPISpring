package com.mutualmobile.praxisspringboot.communication.fcm

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PushNotificationService {

    @Autowired
    lateinit var fcmService: FCMService

    private val logger = LoggerFactory.getLogger(PushNotificationService::class.java)

    fun sendPushNotificationToToken(request: List<PushNotificationRequest>) {
        try {
            fcmService.sendMessageToTokens(request)
        } catch (e: Exception) {
            logger.error(e.message)
        }
    }
}