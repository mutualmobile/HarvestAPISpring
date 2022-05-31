package com.mutualmobile.praxisspringboot.services.orgs.impl

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork
import com.mutualmobile.praxisspringboot.data.user.HarvestUserProject
import com.mutualmobile.praxisspringboot.entities.projects.DBUserWork
import com.mutualmobile.praxisspringboot.entities.user.DBUserProject
import com.mutualmobile.praxisspringboot.repositories.UserProjectRepository
import com.mutualmobile.praxisspringboot.repositories.orgs.UserWorkRepository
import com.mutualmobile.praxisspringboot.services.orgs.UserProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UserProjectServiceImpl : UserProjectService {
    @Autowired
    lateinit var userProjectRepository: UserProjectRepository

    @Autowired
    lateinit var userWorkRepository: UserWorkRepository

    override fun assignProjectToUser(
        projectId: String,
        userId: String
    ): ResponseEntity<ApiResponse<HarvestUserProject>> {
        return try {
            val dbProject = userProjectRepository.findByProjectIdAndUserId(projectId = projectId, userId = userId)
            val doesUserProjectExist: Boolean = dbProject != null

            if (doesUserProjectExist) throw Exception("The specified user is already working in the given project!")

            val result = userProjectRepository.save(DBUserProject(userId = userId, projectId = projectId))
            ResponseEntity.ok(ApiResponse(message = "Assigned successfully!", data = result.toHarvestUserProject()))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ApiResponse(message = e.message))
        }
    }

    override fun findUserLinkedProject(
        projectId: String,
        userId: String
    ): HarvestUserProject? {
        return userProjectRepository
            .findByProjectIdAndUserId(
                projectId = projectId,
                userId = userId
            )?.toHarvestUserProject()
    }

    override fun logWorkTime(harvestUserWork: HarvestUserWork): ApiResponse<Unit> {
        return try {
            userWorkRepository.save(harvestUserWork.toDbUserWork())
            ApiResponse(message = "Work time logging successful!", data = Unit)
        } catch (e: Exception) {
            ApiResponse(message = buildString {
                append("Couldn't log work time.")
                e.localizedMessage?.let { nnExceptionMsg ->
                    append(" Reason: $nnExceptionMsg")
                }
            })
        }
    }
}

fun DBUserProject.toHarvestUserProject() = HarvestUserProject(
    id = id,
    userId = userId,
    projectId = projectId
)

fun HarvestUserProject.toDbUserProject() = DBUserProject(
    userId = userId, projectId = projectId
).apply {
    this.id = this@toDbUserProject.id
}

fun DBUserWork.toHarvestUserWork() = HarvestUserWork(
    id = id,
    projectId = projectId,
    userId = userId,
    workDate = workDate,
    workHours = workHours,
    note = note
)

fun HarvestUserWork.toDbUserWork() = DBUserWork(
    projectId = projectId,
    userId = userId,
    workDate = workDate,
    workHours = workHours,
    note = note
).apply {
    this@toDbUserWork.id?.let { nnId ->
        this.id = nnId
    }
}