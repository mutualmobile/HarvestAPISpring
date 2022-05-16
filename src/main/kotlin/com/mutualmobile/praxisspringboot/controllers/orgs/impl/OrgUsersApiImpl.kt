package com.mutualmobile.praxisspringboot.controllers.orgs.impl

import com.mutualmobile.praxisspringboot.controllers.orgs.OrgUsersApi
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationUser

class OrgUsersApiImpl : OrgUsersApi {
    override fun getOrgUsers(orgId: String): List<OrganizationUser> {
        TODO("Not yet implemented")
    }

    override fun createOrganizationUser(newUser: OrganizationUser): ApiResponse<OrganizationUser> {
        TODO("Not yet implemented")
    }

    override fun updateOrganizationUser(updatedUser: OrganizationUser): ApiResponse<OrganizationUser> {
        TODO("Not yet implemented")
    }
}