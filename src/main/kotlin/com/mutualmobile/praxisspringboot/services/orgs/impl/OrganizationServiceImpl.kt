package com.mutualmobile.praxisspringboot.services.orgs.impl

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.HarvestOrganization
import com.mutualmobile.praxisspringboot.entities.orgs.DBOrganization
import com.mutualmobile.praxisspringboot.repositories.orgs.OrgRepository
import com.mutualmobile.praxisspringboot.services.orgs.OrganizationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull

@Service
class OrganizationServiceImpl : OrganizationService {

    @Autowired
    lateinit var orgRepository: OrgRepository

    override fun createOrganization(harvestOrganization: HarvestOrganization): HarvestOrganization {
        val organization = orgRepository.save(harvestOrganization.toDBHarvestOrganization())
        return organization.toHarvestOrg()
    }

    override fun updateOrganization(harvestOrganization: HarvestOrganization): HarvestOrganization? {
        val org = orgRepository.findByIdOrNull(harvestOrganization.id)
        org?.let {
            val organization = orgRepository.save(
                org.copy(
                    name = harvestOrganization.name,
                    website = harvestOrganization.website,
                    imgUrl = harvestOrganization.imgUrl
                )
            )
            return organization.toHarvestOrg()
        }
        return null
    }

    override fun findOrganization(identifier: String): HarvestOrganization? {
        val currentOrg = orgRepository.findByIdentifier(identifier)
        return if (currentOrg?.deleted == false) currentOrg.toHarvestOrg() else null
    }

    override fun deleteOrganization(organizationId: String): ApiResponse<Boolean> {
        val currentOrg = orgRepository.findById(organizationId).orElse(null)
        currentOrg?.let { nnCurrentOrg ->
            return if (nnCurrentOrg.deleted) {
                ApiResponse(message = "Organisation already deleted", data = false)
            } else {
                orgRepository.save(nnCurrentOrg.apply { deleted = true })
                ApiResponse(message = "Organisation deleted successfully", data = true)
            }
        }
        return ApiResponse(message = "No organisation(s) found with the given id", data = null)
    }

    override fun listOrganizations(
        offset: Int,
        limit: Int,
        search: String?,
    ): Page<DBOrganization> {
        val order: Sort.Order = Sort.Order(Sort.Direction.ASC, "name")
        search?.let {
            return orgRepository.findAllByName(
                search,
                PageRequest.of(offset, limit, Sort.by(order))
            )
        } ?: run {
            return orgRepository.findAll(PageRequest.of(offset, limit, Sort.by(order)))
        }

    }
}

private fun HarvestOrganization.toDBHarvestOrganization(): DBOrganization {
    return DBOrganization(this.name, this.website, this.imgUrl, this.identifier)
}

fun DBOrganization.toHarvestOrg(): HarvestOrganization {
    return HarvestOrganization(this.name, this.website, this.imgUrl, this.id, this.identifier)
}
