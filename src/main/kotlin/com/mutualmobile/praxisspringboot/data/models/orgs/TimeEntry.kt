package com.mutualmobile.praxisspringboot.data.models.orgs

data class TimeEntry(
    val orgId: String,
    val userId: String,
    val notes: String,
    val time: Double,
    val workType: String,
)// TODO add more to this request
