package com.mutualmobile.praxisspringboot.services.orgs

import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationProject

interface OrganizationProjectService {
    fun createProject(organizationProject: OrganizationProject): OrganizationProject
}