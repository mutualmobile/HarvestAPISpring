package com.mutualmobile.praxisspringboot.controllers.orgs

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.projects.DateRangeWorkRequest
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

interface UserWorkApi {
    @PostMapping(Endpoint.LOG_WORK)// fix this later, it should be a GET TODO
    fun getWorkLogsForDateRange(
        @RequestBody dateRangeWorkRequest: DateRangeWorkRequest
    ): ResponseEntity<ApiResponse<List<HarvestUserWork>>>
}