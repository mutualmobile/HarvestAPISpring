package com.mutualmobile.praxisspringboot.controllers.orgs.impl

import com.mutualmobile.praxisspringboot.controllers.authuser.getToken
import com.mutualmobile.praxisspringboot.controllers.orgs.OrgProjectsApi
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationProject
import com.mutualmobile.praxisspringboot.data.models.projects.OrgProjectsRequest
import com.mutualmobile.praxisspringboot.data.user.RequestUser
import com.mutualmobile.praxisspringboot.services.orgs.OrganizationProjectService
import com.mutualmobile.praxisspringboot.services.orgs.UserProjectService
import com.mutualmobile.praxisspringboot.services.user.UserAuthService
import com.mutualmobile.praxisspringboot.services.user.UserDataService
import javax.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class OrgProjectsApiImpl : OrgProjectsApi {

    @Autowired
    lateinit var organizationProjectService: OrganizationProjectService

    @Autowired
    lateinit var userAuthService: UserAuthService

    @Autowired
    lateinit var userDataService: UserDataService

    @Autowired
    lateinit var userProjectService: UserProjectService

    override fun getProjects(
        orgId: String?,
        offset: Int?,
        limit: Int?,
        search: String?,
        httpServletRequest: HttpServletRequest
    ): ApiResponse<Pair<Int, List<OrganizationProject>>> {
        val safeOffset = offset ?: 0
        val safeLimit = limit ?: 10

        val organizationId = orgId ?: try {
            val token = httpServletRequest.getToken() ?: throw Exception()
            userAuthService.getDbUser(token)?.orgId ?: throw Exception()
        } catch (e: Exception) {
            return ApiResponse(message = "No organization found!")
        }

        val result = organizationProjectService
            .getAllProjects(
                organizationId = organizationId,
                offset = safeOffset,
                limit = safeLimit,search=search
            )
        return ApiResponse(data = result)
    }

    override fun getProjectsFromIds(orgProjectsRequest: OrgProjectsRequest): ApiResponse<List<OrganizationProject>> {
        return try {
            ApiResponse(data = userProjectService.getProjectsForIds(orgProjectsRequest.projectIds))
        } catch (e: Exception) {
            ApiResponse(message = e.localizedMessage ?: "Unexpected error occurred!")
        }
    }

    override fun createProject(
        organizationProject: OrganizationProject,
        request: HttpServletRequest
    ): ApiResponse<OrganizationProject> {
        val token = request.getToken()
        token?.let { nnToken ->
            userAuthService.getDbUser(nnToken)?.let { nnDbUser ->
                val result = organizationProjectService.createProject(
                    organizationProject = organizationProject.copy(organizationId = nnDbUser.orgId)
                )
                return ApiResponse(data = result, message = "Project created")
            }
        }
        return ApiResponse(message = "Couldn't create project!")
    }

    override fun updateProject(organizationProject: OrganizationProject): ResponseEntity<ApiResponse<Unit>> {
        val result = organizationProjectService.updateProject(organizationProject = organizationProject)
        return if (result) {
            ResponseEntity.ok(ApiResponse(message = "Updated project successfully!"))
        } else {
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse(message = "Couldn't update project!"))
        }
    }

    override fun deleteProject(projectId: String): ResponseEntity<ApiResponse<Unit>> {
        val isProjectDeleted = organizationProjectService.deleteProject(projectId)
        return if (isProjectDeleted) {
            ResponseEntity.ok(ApiResponse(message = "Deleted project successfully!"))
        } else {
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse(message = "Couldn't delete project!"))
        }
    }

    override fun getListOfUsersForAProject(projectId: String): ResponseEntity<ApiResponse<List<RequestUser>>> {
        return try {
            val userIds = userProjectService.getAllUserIdsFromProjectId(projectId = projectId)
            if (userIds.isEmpty()) throw Exception("No user Id(s) found for the given projectId!")

            val users = userDataService.getAllUsersById(userIds)
            if (users.isEmpty()) throw Exception("No users found for the given user ID(s)!")

            ResponseEntity.ok(ApiResponse(data = users))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ApiResponse(message = e.localizedMessage))
        }
    }
}