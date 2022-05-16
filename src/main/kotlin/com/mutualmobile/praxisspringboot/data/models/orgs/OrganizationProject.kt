package com.mutualmobile.praxisspringboot.data.models.orgs

import java.util.*

data class OrganizationProject(val name: String,
                               val client: String,
                               val startDate: Date?,
                               val endDate: Date?,
                               val isIndefinite: Boolean)// TODO add more to this request
