package com.mutualmobile.praxisspringboot.services.orgs

import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationProject

interface OrganizationProjectService {
    fun createProject(organizationProject: OrganizationProject): OrganizationProject
    fun getAllProjects(organizationId: String, offset: Int, limit: Int): Pair<Int, List<OrganizationProject>>
    fun updateProject(organizationProject: OrganizationProject): Boolean
    fun deleteProject(projectId: String): Boolean
    fun getProjectById(projectId: String): OrganizationProject?
    fun checkIfProjectExists(projectId: String): Boolean
}