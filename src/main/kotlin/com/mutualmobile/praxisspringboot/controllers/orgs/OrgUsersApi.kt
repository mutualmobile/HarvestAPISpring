package com.mutualmobile.praxisspringboot.controllers.orgs

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam

interface OrgUsersApi {
    @GetMapping(Endpoint.ORG_USERS)
    fun getOrgUsers(@RequestParam orgId: String): List<OrganizationUser>

    @PostMapping(Endpoint.ORG_USER)
    fun createOrganizationUser(newUser: OrganizationUser): ApiResponse<OrganizationUser>

    @PutMapping(Endpoint.ORG_USER)
    fun updateOrganizationUser(updatedUser: OrganizationUser): ApiResponse<OrganizationUser>
}