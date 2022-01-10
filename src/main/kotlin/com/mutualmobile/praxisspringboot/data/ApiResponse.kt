package com.mutualmobile.praxisspringboot.data

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(var message: String? = null, var data: T? = null)