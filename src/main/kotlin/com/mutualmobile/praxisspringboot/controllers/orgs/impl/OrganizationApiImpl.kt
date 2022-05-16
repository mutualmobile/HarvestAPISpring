package com.mutualmobile.praxisspringboot.controllers.orgs.impl

import com.mutualmobile.praxisspringboot.controllers.orgs.OrganizationApi
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.HarvestOrganization
import com.mutualmobile.praxisspringboot.entities.orgs.DBOrganization

class OrganizationApiImpl : OrganizationApi {
    override fun getOrganizations(): List<DBOrganization> {
        TODO("Not yet implemented")
    }

    override fun createOrganization(harvestOrganization: HarvestOrganization): ApiResponse<HarvestOrganization> {
        TODO("Not yet implemented")
    }

    override fun updateOrganization(harvestOrganization: HarvestOrganization): ApiResponse<HarvestOrganization> {
        TODO("Not yet implemented")
    }
}