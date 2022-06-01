package com.mutualmobile.praxisspringboot.services.orgs

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork
import java.util.Date

interface UserWorkService {
    fun getWorkLogsForDateRange(
        startDate: Date,
        endDate: Date,
        userIds: List<String>? = null
    ): ApiResponse<List<HarvestUserWork>>
}