package com.mutualmobile.praxisspringboot.controllers.orgs

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.HarvestOrganization
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam

interface OrganizationApi {
    @GetMapping(Endpoint.ORGANIZATIONS)
    fun getOrganizations(
        @RequestParam(value = Endpoint.Params.OFFSET, required = false) offset: Int?,
        @RequestParam(value = Endpoint.Params.LIMIT, required = false) limit: Int?,
        @RequestParam(value = Endpoint.Params.SEARCH_KEY, required = false) search: String?,
    ): List<HarvestOrganization>

    @PostMapping(Endpoint.ORGANIZATION)
    fun createOrganization(harvestOrganization: HarvestOrganization): ApiResponse<HarvestOrganization>

    @PutMapping(Endpoint.ORGANIZATION)
    fun updateOrganization(harvestOrganization: HarvestOrganization): ApiResponse<HarvestOrganization>

    @GetMapping(Endpoint.UN_AUTH_ORGANISATION)
    fun findOrganization(
        @RequestParam(value = Endpoint.Params.ORG_IDENTIFIER, required = true) identifier: String
    ): ResponseEntity<ApiResponse<HarvestOrganization>>

    @DeleteMapping(Endpoint.UN_AUTH_ORGANISATION)
    fun deleteOrganisation(
        @RequestParam(value = Endpoint.Params.ID, required = true) organisationId: String
    ): ResponseEntity<ApiResponse<Boolean>>
}