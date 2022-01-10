package com.mutualmobile.praxisspringboot.data.models.auth

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AuthResponse(val token: String? = null, val message: String? = null, val refreshToken: String? = null)