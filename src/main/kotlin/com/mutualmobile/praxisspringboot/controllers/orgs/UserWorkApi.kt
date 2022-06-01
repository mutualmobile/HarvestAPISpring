package com.mutualmobile.praxisspringboot.controllers.orgs

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.projects.DateRangeWorkRequest
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody

interface UserWorkApi {
    @GetMapping(Endpoint.LOG_WORK)
    fun getWorkLogsForDateRange(
        @RequestBody dateRangeWorkRequest: DateRangeWorkRequest
    ): ResponseEntity<ApiResponse<List<HarvestUserWork>>>
}