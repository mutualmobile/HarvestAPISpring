package com.mutualmobile.praxisspringboot.controllers.orgs.impl

import com.mutualmobile.praxisspringboot.controllers.orgs.OrganizationApi
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.HarvestOrganization
import com.mutualmobile.praxisspringboot.services.orgs.OrganizationService
import com.mutualmobile.praxisspringboot.services.orgs.impl.toHarvestOrg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

@RestController
class OrganizationApiImpl : OrganizationApi {

    @Autowired
    lateinit var organizationService: OrganizationService

    override fun getOrganizations(offset: Int?, limit: Int?, search: String?): List<HarvestOrganization> {
        val page = organizationService.listOrganizations(offset ?: 0, limit ?: 10,search)
        return page.toList().map { it.toHarvestOrg() }
    }

    override fun createOrganization(harvestOrganization: HarvestOrganization): ApiResponse<HarvestOrganization> {
        TODO("Not yet implemented")
    }

    override fun updateOrganization(harvestOrganization: HarvestOrganization): ApiResponse<HarvestOrganization> {
        TODO("Not yet implemented")
    }
}