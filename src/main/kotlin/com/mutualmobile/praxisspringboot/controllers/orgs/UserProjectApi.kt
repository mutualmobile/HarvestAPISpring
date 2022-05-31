package com.mutualmobile.praxisspringboot.controllers.orgs

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork
import com.mutualmobile.praxisspringboot.data.user.HarvestUserProject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

interface UserProjectApi {
    @PostMapping(Endpoint.ASSIGN_PROJECT)
    fun assignProjectToUser(
        @RequestParam projectId: String,
        @RequestParam userId: String
    ): ResponseEntity<ApiResponse<HarvestUserProject>>

    @PostMapping(Endpoint.LOG_WORK)
    fun logWorkTime(@RequestBody userWork: HarvestUserWork): ResponseEntity<ApiResponse<Unit>>
}