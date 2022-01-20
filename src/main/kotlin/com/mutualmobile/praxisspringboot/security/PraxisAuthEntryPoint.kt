package com.mutualmobile.praxisspringboot.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class PraxisAuthEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        if (authException is InternalAuthenticationServiceException || authException is BadCredentialsException) {
            response?.contentType = MediaType.APPLICATION_JSON_VALUE
            response?.status = HttpServletResponse.SC_UNAUTHORIZED
            val body: MutableMap<String, Any> = HashMap()
            body["message"] = "Email or password is incorrect! Please retry."
            val mapper = ObjectMapper()
            mapper.writeValue(response?.outputStream, body)
        } else {
            response?.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                authException?.message
            )
        }
    }
}