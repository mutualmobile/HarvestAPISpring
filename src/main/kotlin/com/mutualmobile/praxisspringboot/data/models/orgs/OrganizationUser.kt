package com.mutualmobile.praxisspringboot.data.models.orgs

import java.util.*

data class OrganizationUser(
    val platformUserId: String,
    val orgId: String,
    val orgEmail: String,
    val avatarUrl: String
)//TODO update this with required request params.
