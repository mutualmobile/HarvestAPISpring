package com.mutualmobile.praxisspringboot.controllers.orgs

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationProject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam

interface OrgProjectsApi {
    @GetMapping
    fun getProjects(@RequestParam orgId: String): ApiResponse<List<OrganizationProject>>

    @PostMapping
    fun createProject(organizationProject: OrganizationProject): ApiResponse<OrganizationProject>

    @PutMapping
    fun updateProject(organizationProject: OrganizationProject): ApiResponse<OrganizationProject>
}