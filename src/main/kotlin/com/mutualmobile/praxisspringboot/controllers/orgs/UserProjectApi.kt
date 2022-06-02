package com.mutualmobile.praxisspringboot.controllers.orgs

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationProject
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork
import javax.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

interface UserProjectApi {
    @PostMapping(Endpoint.ASSIGN_PROJECT)
    fun assignProjectsToUsers(
        // HashMap<ProjectId, List<UserId>>
        @RequestBody workList: HashMap<String, List<String>>
    ): ResponseEntity<ApiResponse<Unit>>

    @PostMapping(Endpoint.LOG_WORK)
    fun logWorkTime(@RequestBody userWork: HarvestUserWork): ResponseEntity<ApiResponse<Unit>>

    @GetMapping(Endpoint.USER_ASSIGNED_PROJECTS)
    fun getUserAssignedProjects(
        @RequestParam userId: String? = null,
        // We don't need to ask for an ID if the user himself is calling this API
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<ApiResponse<List<OrganizationProject>>>
}