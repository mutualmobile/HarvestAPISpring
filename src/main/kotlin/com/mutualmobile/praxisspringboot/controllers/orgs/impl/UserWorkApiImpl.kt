package com.mutualmobile.praxisspringboot.controllers.orgs.impl

import com.mutualmobile.praxisspringboot.controllers.orgs.UserWorkApi
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.projects.DateRangeWorkRequest
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork
import com.mutualmobile.praxisspringboot.services.orgs.UserWorkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UserWorkApiImpl : UserWorkApi {
    @Autowired
    lateinit var userWorkService: UserWorkService

    override fun getWorkLogsForDateRange(
        dateRangeWorkRequest: DateRangeWorkRequest
    ): ResponseEntity<ApiResponse<List<HarvestUserWork>>> {
        return try {
            val result = userWorkService.getWorkLogsForDateRange(
                startDate = dateRangeWorkRequest.startDate,
                endDate = dateRangeWorkRequest.endDate,
                userIds = dateRangeWorkRequest.userIds
            )
            if (result.data == null) {
                ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(result)
            }
            ResponseEntity.ok(result)
        } catch (e: Exception) {
            ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(ApiResponse(message = e.localizedMessage))
        }
    }
}