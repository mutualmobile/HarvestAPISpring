package com.mutualmobile.praxisspringboot.services.orgs

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.HarvestOrganization
import com.mutualmobile.praxisspringboot.entities.orgs.DBOrganization
import org.springframework.data.domain.Page

interface OrganizationService {
    fun listOrganizations(offset: Int, limit: Int, search: String?): Page<DBOrganization>
    fun createOrganization(harvestOrganization: HarvestOrganization) : HarvestOrganization
    fun updateOrganization(harvestOrganization: HarvestOrganization): HarvestOrganization?
    fun findOrganization(identifier: String): HarvestOrganization?
    /** Soft deletes an organisation
     * @param [organizationId] ID of the organisation we need to delete
     * @return [ApiResponse] - Whether the organisation was deleted successfully
     * */
    fun deleteOrganization(organizationId: String): ApiResponse<Boolean>
}