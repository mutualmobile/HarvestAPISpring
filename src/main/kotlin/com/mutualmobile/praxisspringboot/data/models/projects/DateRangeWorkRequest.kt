package com.mutualmobile.praxisspringboot.data.models.projects

import java.util.Date

data class DateRangeWorkRequest(
    val startDate: Date,
    val endDate: Date,
    // To be used as a filter for the output (if exists)
    val userIds: List<String>? = null,
    val workType: String ? = null

)
