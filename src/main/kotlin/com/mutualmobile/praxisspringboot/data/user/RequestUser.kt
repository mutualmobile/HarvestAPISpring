package com.mutualmobile.praxisspringboot.data.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonInclude
import com.mutualmobile.praxisspringboot.data.user.DevicePlatform
import javax.persistence.EnumType
import javax.persistence.Enumerated

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RequestUser(
    @JsonProperty("id")
    var id: String? = null,
    @JsonProperty("firstName")
    var firstName: String? = null,
    @JsonProperty("lastName")
    var lastName: String? = null,
    @JsonProperty("email")
    var email: String? = null,
    @JsonProperty("password")
    var password: String? = null,
    @JsonProperty("role")
    var role: String? = null,
    @JsonProperty("pushToken")
    var pushToken: String? = null,
    @JsonProperty("profilePic")
    var profilePic: String? = null,
    var modifiedTime: String? = null,
    @Enumerated(EnumType.STRING)
    var platform: DevicePlatform? = null,
    var orgId: String
)