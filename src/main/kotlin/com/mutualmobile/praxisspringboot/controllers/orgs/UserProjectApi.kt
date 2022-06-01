package com.mutualmobile.praxisspringboot.controllers.orgs

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface UserProjectApi {
    @PostMapping(Endpoint.ASSIGN_PROJECT)
    fun assignProjectsToUsers(
        // HashMap<ProjectId, List<UserId>>
        @RequestBody workList: HashMap<String, List<String>>
    ): ResponseEntity<ApiResponse<Unit>>

    @PostMapping(Endpoint.LOG_WORK)
    fun logWorkTime(@RequestBody userWork: HarvestUserWork): ResponseEntity<ApiResponse<Unit>>
}