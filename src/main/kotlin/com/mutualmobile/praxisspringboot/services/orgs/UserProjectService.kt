package com.mutualmobile.praxisspringboot.services.orgs

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationProject
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork

interface UserProjectService {
    fun assignProjectsToUsers(
        workList: HashMap<String, List<String>>
    ): ApiResponse<Unit>

    /** Checks whether the given user has been assigned to the given project or not (by searching it in the
     * user_project table). Returns true if a project is found, else returns false. */
    fun checkIfUserLinkedProjectExists(projectId: String, userId: String): Boolean

    fun logWorkTime(harvestUserWork: HarvestUserWork): ApiResponse<Unit>
    fun getAllUserIdsFromProjectId(projectId: String): List<String> // List<UserId>
    fun deleteWork(userWork: HarvestUserWork)
    fun getProjectsForIds(projectIds: List<String>): List<OrganizationProject>
}