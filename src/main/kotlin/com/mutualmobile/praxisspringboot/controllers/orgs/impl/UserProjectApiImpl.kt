package com.mutualmobile.praxisspringboot.controllers.orgs.impl

import com.mutualmobile.praxisspringboot.controllers.orgs.UserProjectApi
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.user.HarvestUserProject
import com.mutualmobile.praxisspringboot.services.orgs.OrganizationProjectService
import com.mutualmobile.praxisspringboot.services.orgs.UserProjectService
import com.mutualmobile.praxisspringboot.services.user.UserDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UserProjectApiImpl : UserProjectApi {
    @Autowired
    lateinit var organizationProjectService: OrganizationProjectService

    @Autowired
    lateinit var userDataService: UserDataService

    @Autowired
    lateinit var userProjectService: UserProjectService

    override fun assignProjectToUser(
        projectId: String,
        userId: String
    ): ResponseEntity<ApiResponse<HarvestUserProject>> {
        val doesProjectExist = organizationProjectService.getProjectById(projectId = projectId) != null
        val doesUserExist = userDataService.getUserById(userId = userId) != null

        if (!doesProjectExist || !doesUserExist) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
            .body(ApiResponse(message = "Either the project or the user doesn't exist for the given ID(s)!"))

        return userProjectService.assignProjectToUser(projectId = projectId, userId = userId)
    }
}