package com.mutualmobile.praxisspringboot.controllers.orgs.impl

import com.mutualmobile.praxisspringboot.controllers.orgs.OrganizationApi
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.HarvestOrganization
import com.mutualmobile.praxisspringboot.services.orgs.OrganizationService
import com.mutualmobile.praxisspringboot.services.orgs.impl.toHarvestOrg
import java.util.Optional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class OrganizationApiImpl : OrganizationApi {

    @Autowired
    lateinit var organizationService: OrganizationService

    override fun getOrganizations(offset: Int?, limit: Int?, search: String?): List<HarvestOrganization> {
        val page = organizationService.listOrganizations(offset ?: 0, limit ?: 10, search)
        return page.toList().filterNot { it.deleted }.map { it.toHarvestOrg() }
    }

    override fun createOrganization(harvestOrganization: HarvestOrganization): ApiResponse<HarvestOrganization> {
        val result = organizationService.createOrganization(harvestOrganization)
        return ApiResponse(message = "Organization created", data = result)
    }

    override fun updateOrganization(harvestOrganization: HarvestOrganization): ApiResponse<HarvestOrganization> {
        val result = organizationService.updateOrganization(harvestOrganization)
        return ApiResponse(message = "Organization updated", data = result)
    }

    override fun findOrganization(identifier: String): ResponseEntity<ApiResponse<HarvestOrganization>> {
        val result = organizationService.findOrganization(identifier)
        result?.let { nnResult ->
            return ResponseEntity.ok(ApiResponse(message = "Organization already exists", data = nnResult))
        }
        return ResponseEntity.noContent().build()
    }

    override fun deleteOrganisation(organisationId: String): ResponseEntity<ApiResponse<Boolean>> {
        val result = organizationService.deleteOrganization(organisationId)
        return if (result.data != null) ResponseEntity.ok(result.copy(data = null))
        else ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(result)
    }
}