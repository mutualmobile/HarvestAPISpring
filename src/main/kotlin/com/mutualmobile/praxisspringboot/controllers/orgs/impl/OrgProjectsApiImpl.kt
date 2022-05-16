package com.mutualmobile.praxisspringboot.controllers.orgs.impl

import com.mutualmobile.praxisspringboot.controllers.orgs.OrgProjectsApi
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationProject

class OrgProjectsApiImpl : OrgProjectsApi {
    override fun getProjects(orgId: String): ApiResponse<List<OrganizationProject>> {
        TODO("Not yet implemented")
    }

    override fun createProject(organizationProject: OrganizationProject): ApiResponse<OrganizationProject> {
        TODO("Not yet implemented")
    }

    override fun updateProject(organizationProject: OrganizationProject): ApiResponse<OrganizationProject> {
        TODO("Not yet implemented")
    }
}