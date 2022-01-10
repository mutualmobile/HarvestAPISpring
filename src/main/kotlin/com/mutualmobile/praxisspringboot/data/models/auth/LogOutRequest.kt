package com.mutualmobile.praxisspringboot.data.models.auth

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class LogOutRequest(val userId: String? = null, val pushToken: String?)