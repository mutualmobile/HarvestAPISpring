package com.mutualmobile.praxisspringboot.controllers.orgs

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.HarvestOrganization
import com.mutualmobile.praxisspringboot.entities.orgs.DBOrganization
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping

interface OrganizationApi {
    @GetMapping(Endpoint.ORGANIZATIONS)
    fun getOrganizations(): List<DBOrganization>

    @PostMapping(Endpoint.ORGANIZATION)
    fun createOrganization(harvestOrganization: HarvestOrganization): ApiResponse<HarvestOrganization>

    @PutMapping(Endpoint.ORGANIZATION)
    fun updateOrganization(harvestOrganization: HarvestOrganization): ApiResponse<HarvestOrganization>
}