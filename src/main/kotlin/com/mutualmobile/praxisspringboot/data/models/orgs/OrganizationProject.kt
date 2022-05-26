package com.mutualmobile.praxisspringboot.data.models.orgs

import java.util.Date

data class OrganizationProject(
    val name: String,
    val client: String,
    val startDate: Date?,
    val endDate: Date?,
    val isIndefinite: Boolean,
    val organizationId: String? = null,
)// TODO add more to this request
