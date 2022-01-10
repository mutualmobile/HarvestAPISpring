package com.mutualmobile.praxisspringboot.communication.email

import com.mutualmobile.praxisspringboot.communication.fcm.PushNotificationRequest
import com.mutualmobile.praxisspringboot.communication.fcm.PushNotificationService
import com.mutualmobile.praxisspringboot.entities.notification.DBNotification
import com.mutualmobile.praxisspringboot.repositories.FCMRepository
import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder
import com.mutualmobile.praxisspringboot.repositories.NotificationsRepository
import net.minidev.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.Executors
import javax.mail.util.ByteArrayDataSource


@Service
class PraxisNotificationService {

    @Value("\${from.email.address}")
    private val fromEmailAddress: String? = null

    @Autowired
    private lateinit var emailSender: JavaMailSender

    @Autowired
    lateinit var fcmRepository: FCMRepository

    @Autowired
    lateinit var pushNotificationService: PushNotificationService

    @Autowired
    private lateinit var templateEngine: SpringTemplateEngine

    @Autowired
    lateinit var notificationsRepository: NotificationsRepository

    private val quickService = Executors.newCachedThreadPool()

    private val log: Logger = LoggerFactory.getLogger(PraxisNotificationService::class.java)

    fun attachmentData(context: Context): Pair<ByteArrayDataSource?, String?> {
        return try {
            val html: String = templateEngine.process("defaulttemplate", context)
            val outputStream = ByteArrayOutputStream()
            val builder = PdfRendererBuilder()
            builder.useFastMode()
            builder.useDefaultPageSize(
                148.0F,
                210F,
                BaseRendererBuilder.PageSizeUnits.MM
            )
            builder.withHtmlContent(html, "")
            builder.toStream(outputStream)
            builder.run()

            val pdfBytes = outputStream.toByteArray()
            val body: String = Base64.getEncoder().encodeToString(pdfBytes)

            val inputStream = ByteArrayInputStream(pdfBytes)
            val attachment = ByteArrayDataSource(inputStream, "application/octet-stream");
            outputStream.close()
            Pair(attachment, body.length.toString())
        } catch (ex: Exception) {
            ex.printStackTrace()
            Pair(null, null)
        }

    }


    private fun sendEmailInternal(PraxisNotification: PraxisNotification, file: ByteArrayDataSource? = null) {
        try {
            val message = emailSender.createMimeMessage()
            val helper = MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
            )
            val context = Context()
            context.setVariables(PraxisNotification.props)
            val html: String = templateEngine.process(PraxisNotification.template, context)
            val internetAddresses = PraxisNotification.mailTo.toTypedArray()
            helper.setTo(internetAddresses.first())
            helper.setBcc(internetAddresses)
            helper.setText(html, true)
            helper.setSubject(PraxisNotification.subject)
            helper.setFrom(fromEmailAddress!!, "PraxisConnect Support")
            log.info("sending email to ${internetAddresses.first()}")
            file?.let {
                helper.addAttachment("Invoice.pdf", file)
            }
            emailSender.send(message)
            log.info("sent email to ${internetAddresses.first()}")
        } catch (ex: Exception) {
            ex.printStackTrace()
            log.error(ex.toString())
        }
    }

    fun sendEmail(PraxisNotification: PraxisNotification, file: ByteArrayDataSource? = null) {
        quickService.submit {
            sendEmailInternal(PraxisNotification, file)
        }
    }

    fun saveAndSendPushNotification(notification: DBNotification) {
        quickService.submit {
            notificationsRepository.save(notification)
            val tokens = fcmRepository.findAllByUserIds(listOf(notification.userId!!))
            val requests = tokens.filter { it.token != null }.map {
                PushNotificationRequest(
                    notification.title,
                    notification.body,
                    null,
                    it,
                    hashMapOf<String, String>().apply {
                        put("click_action", "FLUTTER_NOTIFICATION_CLICK")
                        this["data"] = JSONObject().apply {
                            put("id", notification.payload)
                            put("click_action", "FLUTTER_NOTIFICATION_CLICK")
                            put("type", notification.notificationType.name)
                        }.toString()
                    })
            }
            pushNotificationService.sendPushNotificationToToken(requests)
        }
    }

}