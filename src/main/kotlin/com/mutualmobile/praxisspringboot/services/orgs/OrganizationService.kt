package com.mutualmobile.praxisspringboot.services.orgs

import com.mutualmobile.praxisspringboot.data.models.orgs.HarvestOrganization
import com.mutualmobile.praxisspringboot.entities.orgs.DBOrganization
import org.springframework.data.domain.Page

interface OrganizationService {
    fun listOrganizations(offset: Int, limit: Int, search: String?): Page<DBOrganization>
    fun createOrganization(harvestOrganization: HarvestOrganization) : HarvestOrganization
    fun updateOrganization(harvestOrganization: HarvestOrganization): HarvestOrganization?
}