package com.mutualmobile.praxisspringboot.services.orgs

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork
import com.mutualmobile.praxisspringboot.data.user.HarvestUserProject
import org.springframework.http.ResponseEntity

interface UserProjectService {
    fun assignProjectToUser(
        projectId: String,
        userId: String
    ): ResponseEntity<ApiResponse<HarvestUserProject>>

    /** Checks whether the given user has been assigned to the given project or not (by searching it in the
     * user_project table). Returns [HarvestUserProject] if a project is found, else returns null. */
    fun findUserLinkedProject(projectId: String, userId: String): HarvestUserProject?

    fun logWorkTime(harvestUserWork: HarvestUserWork): ApiResponse<Unit>
}