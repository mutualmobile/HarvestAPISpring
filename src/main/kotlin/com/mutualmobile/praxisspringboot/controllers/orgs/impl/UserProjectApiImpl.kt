package com.mutualmobile.praxisspringboot.controllers.orgs.impl

import com.mutualmobile.praxisspringboot.controllers.orgs.UserProjectApi
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork
import com.mutualmobile.praxisspringboot.data.user.HarvestUserProject
import com.mutualmobile.praxisspringboot.services.orgs.OrganizationProjectService
import com.mutualmobile.praxisspringboot.services.orgs.UserProjectService
import com.mutualmobile.praxisspringboot.services.user.UserDataService
import java.util.Date
import javax.servlet.http.HttpServletRequest
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

    override fun logWorkTime(
        projectId: String,
        workDate: Date,
        workHours: Int,
        note: String?,
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<ApiResponse<Unit>> {
        return try {
            val userId =
                userDataService.getUser(httpServletRequest)?.id
                    ?: throw Exception("No user found with the given ID!")

            val userProjectId = userProjectService.findUserProject(projectId, userId)?.id
                ?: throw Exception("Either no project exists with the given ID or the current user hasn't been assigned to it!")

            val result = userProjectService.logWorkTime(
                HarvestUserWork(
                    userProjectId = userProjectId,
                    workDate = workDate,
                    workHours = workHours,
                    note = note
                )
            )

            if (result.data == null) {
                ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(result)
            } else {
                ResponseEntity.ok(result)
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ApiResponse(message = e.localizedMessage))
        }
    }
}