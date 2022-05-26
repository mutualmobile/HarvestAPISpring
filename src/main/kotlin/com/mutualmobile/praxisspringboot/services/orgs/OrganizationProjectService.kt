package com.mutualmobile.praxisspringboot.services.orgs

import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationProject

interface OrganizationProjectService {
    fun createProject(organizationProject: OrganizationProject): OrganizationProject
    fun getAllProjects(organizationId: String, offset: Int, limit: Int): List<OrganizationProject>
}