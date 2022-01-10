package com.mutualmobile.praxisspringboot.communication.fcm

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
class FCMInitializer {
    @Value("\${app.firebase-configuration-file}")
    lateinit var firebaseConfigPath: String
    private val logger = LoggerFactory.getLogger(FCMInitializer::class.java)

    @PostConstruct
    fun initialize() {
        // TODO setup with real firebase app
       /* try {
            val options: FirebaseOptions = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(ClassPathResource(firebaseConfigPath).inputStream))
                .build()
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
                logger.info("Firebase application has been initialized")
            }
        } catch (e: IOException) {
            logger.error(e.message)
        }*/
    }
}