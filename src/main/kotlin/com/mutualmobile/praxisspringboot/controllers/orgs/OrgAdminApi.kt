package com.mutualmobile.praxisspringboot.controllers.orgs

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.data.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

interface OrgAdminApi {
    @PostMapping(Endpoint.ASSIGN_PROJECT)
    fun assignProjectToUser(
        @RequestParam projectId: String,
        @RequestParam userId: String
    ): ResponseEntity<ApiResponse<Void>>
}