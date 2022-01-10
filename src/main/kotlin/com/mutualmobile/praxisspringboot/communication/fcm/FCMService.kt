package com.mutualmobile.praxisspringboot.communication.fcm

import com.google.firebase.messaging.*
import com.mutualmobile.praxisspringboot.data.user.DevicePlatform
import com.mutualmobile.praxisspringboot.repositories.FCMRepository
import org.springframework.beans.factory.annotation.Autowired
import kotlin.Throws
import java.lang.InterruptedException
import java.util.concurrent.ExecutionException
import org.springframework.stereotype.Service

@Service
class FCMService {

    @Autowired
    lateinit var fcmRepository: FCMRepository

    @Throws(InterruptedException::class, ExecutionException::class)
    fun sendMessageToTokens(request: List<PushNotificationRequest?>) {
        request.asSequence().chunked(99).forEach { chunkedPnR ->
            val messagesChunked = chunkedPnR.map { request ->
                val message = when (request?.token?.platform) {
                    DevicePlatform.Android -> {
                        androidMessage(request)
                    }
                    DevicePlatform.Web -> {
                        webMessage(request)
                    }
                    DevicePlatform.iOS -> {
                        iOSMessage(request)
                    }
                    else -> {
                        webMessage(request)
                    }
                }
                Pair(request?.token?.id, message)
            }
            val result = FirebaseMessaging.getInstance().sendAll(messagesChunked.map { it.second })
            val deprecatedIds = mutableListOf<String>()
            result.responses.forEachIndexed { index, sendResponse ->
                if (sendResponse.exception != null && sendResponse.exception.errorCode != null) {
                    messagesChunked[index].first?.let { deprecatedIds.add(it) }
                }
            }
            fcmRepository.deleteAllById(deprecatedIds)
        }
    }

    private fun iOSMessage(request: PushNotificationRequest?): Message? {
        val webPush =
            ApnsConfig.builder().setAps(
                Aps.builder().setAlert(ApsAlert.builder().setTitle(request?.title).setBody(request?.message).build())
                    .putAllCustomData(request?.data)
                    .build()
            ).build()

        return Message.builder()
            .setApnsConfig(webPush)
            .putAllData(request?.data)
            .setNotification(Notification.builder().setTitle(request?.title).setBody(request?.message).build())
            .setToken(request?.token?.token).build()
    }

    private fun androidMessage(request: PushNotificationRequest?): Message? {
        val webPush =
            AndroidConfig.builder()
                .setNotification(
                    AndroidNotification.builder().setTitle(request?.title).setBody(request?.message).build()
                ).putAllData(request?.data).build()
        return Message.builder()
            .setAndroidConfig(webPush)
            .putAllData(request?.data)
            .setNotification(Notification.builder().setTitle(request?.title).setBody(request?.message).build())
            .setToken(request?.token?.token).build()
    }

    private fun webMessage(request: PushNotificationRequest?): Message? {
        val webPush =
            WebpushConfig.builder()
                .setNotification(WebpushNotification(request?.title, request?.message))
                .putAllData(request?.data)
                .build()
        return Message.builder()
            .setWebpushConfig(webPush)
            .putAllData(request?.data)
            .setNotification(Notification.builder().setTitle(request?.title).setBody(request?.message).build())
            .setToken(request?.token?.token).build()
    }

}