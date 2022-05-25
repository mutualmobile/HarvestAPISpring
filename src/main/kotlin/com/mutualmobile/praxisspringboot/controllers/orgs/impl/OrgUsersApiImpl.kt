package com.mutualmobile.praxisspringboot.controllers.orgs.impl

import com.mutualmobile.praxisspringboot.controllers.authuser.getToken
import com.mutualmobile.praxisspringboot.controllers.orgs.OrgUsersApi
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationUser
import com.mutualmobile.praxisspringboot.data.user.RequestUser
import com.mutualmobile.praxisspringboot.services.orgs.OrganizationService
import com.mutualmobile.praxisspringboot.services.user.UserAuthService
import com.mutualmobile.praxisspringboot.services.user.UserDataService
import javax.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

@RestController
class OrgUsersApiImpl : OrgUsersApi {
    @Autowired
    lateinit var userDataService: UserDataService

    @Autowired
    lateinit var userAuthService: UserAuthService

    @Autowired
    lateinit var organizationService: OrganizationService

    // Might be used later
//    override fun getOrgUsers(orgId: String): List<OrganizationUser> {
//        TODO("Not yet implemented")
//    }

    override fun createOrganizationUser(newUser: OrganizationUser): ApiResponse<OrganizationUser> {
        TODO("Not yet implemented")
    }

    override fun updateOrganizationUser(updatedUser: OrganizationUser): ApiResponse<OrganizationUser> {
        TODO("Not yet implemented")
    }

    override fun getOrgUsersByType(
        userType: String,
        orgIdentifier: String?,
        isUserDeleted: Boolean,
        offset: Int?,
        limit: Int?,
        httpServletRequest: HttpServletRequest
    ): ApiResponse<List<RequestUser>> {
        val organisationIdentifier = orgIdentifier ?: try {
            val token = httpServletRequest.getToken() ?: throw Exception()
            val user = userAuthService.getDbUser(token) ?: throw Exception()
            organizationService.findOrganizationById(user.orgId)?.identifier ?: throw Exception()
        } catch (e: Exception) { null }
        return userDataService.getUsersByTypeAndOrgName(
            userType = userType,
            orgIdentifier = organisationIdentifier,
            isUserDeleted = isUserDeleted,
            offset = offset,
            limit = limit
        )
    }
}