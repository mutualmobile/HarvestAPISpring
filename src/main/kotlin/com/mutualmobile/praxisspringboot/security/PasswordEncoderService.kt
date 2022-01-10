package com.mutualmobile.praxisspringboot.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordEncoderService {

    fun providePWDEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}