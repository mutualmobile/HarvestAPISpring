package com.mutualmobile.praxisspringboot.controllers.orgs.impl

import com.mutualmobile.praxisspringboot.controllers.orgs.OrgProjectsApi
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationProject
import com.mutualmobile.praxisspringboot.services.orgs.OrganizationProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

@RestController
class OrgProjectsApiImpl : OrgProjectsApi {

    @Autowired
    lateinit var organizationProjectService: OrganizationProjectService

    override fun getProjects(orgId: String): ApiResponse<List<OrganizationProject>> {
        TODO("Not yet implemented")
    }

    override fun createProject(organizationProject: OrganizationProject): ApiResponse<OrganizationProject> {
        val result = organizationProjectService.createProject(organizationProject = organizationProject)
        return ApiResponse(data = result, message = "Project created")
    }

    override fun updateProject(organizationProject: OrganizationProject): ApiResponse<OrganizationProject> {
        TODO("Not yet implemented")
    }
}