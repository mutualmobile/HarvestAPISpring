package com.mutualmobile.praxisspringboot.controllers.orgs

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.user.HarvestUserProject
import java.util.Date
import javax.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

interface UserProjectApi {
    @PostMapping(Endpoint.ASSIGN_PROJECT)
    fun assignProjectToUser(
        @RequestParam projectId: String,
        @RequestParam userId: String
    ): ResponseEntity<ApiResponse<HarvestUserProject>>

    @PostMapping(Endpoint.LOG_WORK)
    fun logWorkTime(
        @RequestParam projectId: String,
        @RequestParam workDate: Date,
        @RequestParam workHours: Int,
        @RequestParam note: String? = null,
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<ApiResponse<Unit>>
}