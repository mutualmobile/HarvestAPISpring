package com.mutualmobile.praxisspringboot.controllers.orgs.impl

import com.mutualmobile.praxisspringboot.controllers.orgs.UserProjectApi
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationProject
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork
import com.mutualmobile.praxisspringboot.services.orgs.OrganizationProjectService
import com.mutualmobile.praxisspringboot.services.orgs.UserProjectService
import com.mutualmobile.praxisspringboot.services.orgs.UserWorkService
import com.mutualmobile.praxisspringboot.services.user.UserDataService
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

    @Autowired
    lateinit var userWorkService: UserWorkService

    override fun assignProjectsToUsers(workList: HashMap<String, List<String>>): ResponseEntity<ApiResponse<Unit>> {
        return try {
            workList.forEach { (projectId, userIds) ->
                if (!organizationProjectService.checkIfProjectExists(projectId = projectId)) throw Exception("No project found with the ID: $projectId")
                userIds.forEach { userId ->
                    if (!userDataService.checkIfUserExists(userId = userId)) throw Exception("No user found with the ID: $userId")
                }
            }
            val result = userProjectService.assignProjectsToUsers(workList = workList)
            if (result.data == null) {
                ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(result)
            } else {
                ResponseEntity.ok(result)
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ApiResponse(message = e.localizedMessage))
        }
    }

    override fun editWorkTime(userWork: HarvestUserWork): ResponseEntity<ApiResponse<Unit>> {
        return saveWorkInternal(userWork)
    }

    override fun deleteWorkTime(userWork: HarvestUserWork): ResponseEntity<ApiResponse<Unit>> {
        userProjectService.deleteWork(userWork)
        return ResponseEntity.ok(ApiResponse(message = "The work will be deleted."))
    }

    override fun logWorkTime(userWork: HarvestUserWork): ResponseEntity<ApiResponse<Unit>> {
        return saveWorkInternal(userWork)
    }

    private fun saveWorkInternal(userWork: HarvestUserWork): ResponseEntity<ApiResponse<Unit>> {
        return try {
            val doesUserLinkedProjectExist = userProjectService.checkIfUserLinkedProjectExists(
                projectId = userWork.projectId, userId = userWork.userId
            )
            if (!doesUserLinkedProjectExist) throw Exception(
                "Either no project exists with the given ID or the current user hasn't been assigned to it!"
            )

            val result = userProjectService.logWorkTime(userWork)

            if (result.data == null) {
                ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(result)
            } else {
                ResponseEntity.ok(result)
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ApiResponse(message = e.localizedMessage))
        }
    }

    override fun getUserAssignedProjects(
        userId: String?, httpServletRequest: HttpServletRequest
    ): ResponseEntity<ApiResponse<List<OrganizationProject>>> {
        return try {
            // Give preference to the userId param, if that's null, try using the token to find the ID, if that fails too, throw an exception
            val nnUserId =
                userId ?: userDataService.getUser(httpServletRequest)?.id ?: throw Exception("No user found!")

            val result = userWorkService.getUserAssignedProjects(nnUserId)

            if (result.data == null) throw Exception(result.message)

            ResponseEntity.ok(result)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ApiResponse(message = e.localizedMessage))
        }
    }
}