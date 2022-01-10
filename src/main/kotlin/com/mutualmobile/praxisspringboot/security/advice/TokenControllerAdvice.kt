package com.mutualmobile.praxisspringboot.security.advice

import com.mutualmobile.praxisspringboot.exceptions.TokenRefreshException
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@RestControllerAdvice
class TokenControllerAdvice {
    @ExceptionHandler(value = [TokenRefreshException::class])
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleTokenRefreshException(ex: TokenRefreshException, request: WebRequest): ErrorMessage {
        return ErrorMessage(
            HttpStatus.FORBIDDEN.value(),
            Date(),
            ex.message,
            request.getDescription(false)
        )
    }
}