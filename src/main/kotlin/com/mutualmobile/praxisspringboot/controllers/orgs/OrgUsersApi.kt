package com.mutualmobile.praxisspringboot.controllers.orgs

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationUser
import com.mutualmobile.praxisspringboot.data.user.RequestUser
import javax.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam

interface OrgUsersApi {
    // Might be used later
//    @GetMapping(Endpoint.ORG_USERS)
//    fun getOrgUsers(@RequestParam orgId: String): List<OrganizationUser>

    @PostMapping(Endpoint.ORG_USER)
    fun createOrganizationUser(newUser: OrganizationUser): ApiResponse<OrganizationUser>

    @PutMapping(Endpoint.ORG_USER)
    fun updateOrganizationUser(updatedUser: OrganizationUser): ApiResponse<OrganizationUser>

    @GetMapping(Endpoint.ORG_USERS)
    fun getOrgUsersByType(
        @RequestParam userType: String,
        @RequestParam orgIdentifier: String? = null,
        @RequestParam isUserDeleted: Boolean = false,
        @RequestParam(value = Endpoint.Params.OFFSET, required = false) offset: Int = 0,
        @RequestParam(value = Endpoint.Params.LIMIT, required = false) limit: Int = 10,
        httpServletRequest: HttpServletRequest
    ): ApiResponse<List<RequestUser>>
}