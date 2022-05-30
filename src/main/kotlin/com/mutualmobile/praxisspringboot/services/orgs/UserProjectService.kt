package com.mutualmobile.praxisspringboot.services.orgs

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork
import com.mutualmobile.praxisspringboot.data.user.HarvestUserProject
import org.springframework.http.ResponseEntity

interface UserProjectService {
    fun assignProjectToUser(
        projectId: String,
        userId: String
    ): ResponseEntity<ApiResponse<HarvestUserProject>>

    fun findUserProject(projectId: String, userId: String): HarvestUserProject?

    fun logWorkTime(harvestUserWork: HarvestUserWork): ApiResponse<Unit>
}