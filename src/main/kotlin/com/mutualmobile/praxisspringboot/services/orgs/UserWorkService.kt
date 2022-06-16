package com.mutualmobile.praxisspringboot.services.orgs

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationProject
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork
import com.mutualmobile.praxisspringboot.data.models.projects.WorkType
import java.util.Date

interface UserWorkService {
    fun getWorkLogsForDateRange(
        startDate: Date,
        endDate: Date,
        userIds: List<String>? = null,
        workType: WorkType?=null
    ): ApiResponse<List<HarvestUserWork>>

    fun getUserAssignedProjects(userId: String): ApiResponse<List<OrganizationProject>>
}