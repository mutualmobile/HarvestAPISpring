package com.mutualmobile.praxisspringboot.data.models.auth
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResetPasswordRequest(var token:String?,var password:String?)