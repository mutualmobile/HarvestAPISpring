package com.mutualmobile.praxisspringboot.services.orgs

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.user.HarvestUserProject
import org.springframework.http.ResponseEntity

interface UserProjectService {
    fun assignProjectToUser(
        projectId: String,
        userId: String
    ): ResponseEntity<ApiResponse<HarvestUserProject>>
}