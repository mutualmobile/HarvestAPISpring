package com.mutualmobile.praxisspringboot.controllers.orgs

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationProject
import javax.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

interface OrgProjectsApi {
    @GetMapping(Endpoint.ORG_PROJECT)
    fun getProjects(
        @RequestParam orgId: String?,
        @RequestParam(value = Endpoint.Params.OFFSET, required = false) offset: Int?,
        @RequestParam(value = Endpoint.Params.LIMIT, required = false) limit: Int?,
        httpServletRequest: HttpServletRequest
    ): ApiResponse<List<OrganizationProject>>

    @PostMapping(Endpoint.ORG_PROJECT)
    fun createProject(
        @RequestBody organizationProject: OrganizationProject,
        request: HttpServletRequest
    ): ApiResponse<OrganizationProject>

    @PutMapping(Endpoint.ORG_PROJECT)
    fun updateProject(@RequestBody organizationProject: OrganizationProject): ResponseEntity<ApiResponse<Unit>>

    @DeleteMapping(Endpoint.ORG_PROJECT)
    fun deleteProject(@RequestParam projectId: String): ResponseEntity<ApiResponse<Unit>>
}