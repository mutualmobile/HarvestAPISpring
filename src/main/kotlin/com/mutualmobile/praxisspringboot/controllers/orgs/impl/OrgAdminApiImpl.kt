package com.mutualmobile.praxisspringboot.controllers.orgs.impl

import com.mutualmobile.praxisspringboot.controllers.orgs.OrgAdminApi
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.services.orgs.OrganizationProjectService
import com.mutualmobile.praxisspringboot.services.user.UserDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class OrgAdminApiImpl : OrgAdminApi {
    @Autowired
    lateinit var organizationProjectService: OrganizationProjectService

    @Autowired
    lateinit var userDataService: UserDataService

    override fun assignProjectToUser(
        projectId: String,
        userId: String
    ): ResponseEntity<ApiResponse<Void>> {
        val doesProjectExist = organizationProjectService.getProjectById(projectId = projectId) != null
        if (!doesProjectExist) return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(ApiResponse(message = "No project found with the given projectId!"))
        userDataService.getUserById(userId = userId)?.let { nnUser ->
            val updateResponse = userDataService.updateUser(
                user = nnUser.apply {
                    if (projectIds?.contains(projectId) == false) {
                        projectIds = projectIds?.toMutableList()?.apply {
                            add(projectId)
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                            .body(ApiResponse(message = "User is already working on the given project!"))
                    }
                }
            )
            updateResponse?.let { nnUpdateResponse ->
                return ResponseEntity.ok(nnUpdateResponse)
            } ?: run {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ApiResponse(message = "Couldn't assign project to user!"))
            }
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(ApiResponse(message = "No user found with the given ID!"))
    }
}