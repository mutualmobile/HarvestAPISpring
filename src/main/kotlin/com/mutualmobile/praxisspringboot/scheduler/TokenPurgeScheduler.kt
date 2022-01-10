package com.mutualmobile.praxisspringboot.scheduler

import com.mutualmobile.praxisspringboot.security.RefreshTokenService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.*

@Component
class TokenPurgeScheduler {

    @Autowired
    lateinit var refreshTokenService: RefreshTokenService

    private val logger = LoggerFactory.getLogger(TokenPurgeScheduler::class.java)


    @Scheduled(fixedRate = 1000 * 60 * 60 * 24 * 2)
    fun onTokenPurgeRequested() {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val now = Date()
        val strDate = sdf.format(now)
        logger.info("Fixed Delay scheduler:: $strDate")
        refreshTokenService.deleteObsoleteTokens()
    }
}