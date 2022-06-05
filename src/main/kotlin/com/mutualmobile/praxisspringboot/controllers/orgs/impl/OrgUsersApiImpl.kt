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
import org.springframework.data.domain.PageRequest
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
        offset: Int,
        limit: Int,
        search:String?,
        httpServletRequest: HttpServletRequest
    ): ApiResponse<Pair<Int,List<RequestUser>>> {
        val organizationId: String = orgIdentifier?.let { nnOrgIdentifier ->
            organizationService.findOrganization(nnOrgIdentifier)?.id
                ?: return ApiResponse(message = "No organization found!")
        } ?: try {
            val token = httpServletRequest.getToken() ?: throw Exception()
            val user = userAuthService.getDbUser(token) ?: throw Exception()

            user.orgId
        } catch (e: Exception) {
            return ApiResponse(message = "No organization found!")
        }
        return userDataService.getUsersByTypeAndOrgId(
            userType = userType,
            orgId = organizationId,
            isUserDeleted = isUserDeleted,
            pageable = PageRequest.of(offset, limit),search
        )
    }
}