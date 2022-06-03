package com.mutualmobile.praxisspringboot.services.orgs.impl

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork
import com.mutualmobile.praxisspringboot.data.user.HarvestUserProjectAssignment
import com.mutualmobile.praxisspringboot.entities.projects.DBUserWork
import com.mutualmobile.praxisspringboot.entities.user.DBUserProjectAssignment
import com.mutualmobile.praxisspringboot.repositories.UserProjectRepository
import com.mutualmobile.praxisspringboot.repositories.orgs.UserWorkRepository
import com.mutualmobile.praxisspringboot.services.orgs.UserProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserProjectServiceImpl : UserProjectService {
    @Autowired
    lateinit var userProjectRepository: UserProjectRepository

    @Autowired
    lateinit var userWorkRepository: UserWorkRepository

    override fun assignProjectsToUsers(workList: HashMap<String, List<String>>): ApiResponse<Unit> {
        return try {
            val workToSave = mutableListOf<HarvestUserProjectAssignment>()
            val errorProjectAssignmentIds = mutableListOf<Pair<String, String>>()
            workList.forEach { (projectId, userIds) ->
                userIds.forEach { userId ->
                    val doesProjectAssignmentExist = userProjectRepository.existsByProjectIdAndUserId(projectId, userId)

                    if (!doesProjectAssignmentExist) {
                        workToSave.add(HarvestUserProjectAssignment(projectId = projectId, userId = userId))
                    } else {
                        val errorPair = Pair(projectId, userId)

                        if (!errorProjectAssignmentIds.contains(errorPair)) {
                            errorProjectAssignmentIds.add(errorPair)
                        }
                    }
                }
            }
            userProjectRepository.saveAll(workToSave.map { it.toDbUserProject() })
            if (errorProjectAssignmentIds.isNotEmpty()) {
                throw Exception(
                    "The correct assignments were made successfully but the following project-user assignments already exist: ${
                        errorProjectAssignmentIds.joinToString { "ProjectId:${it.first} - UserId:${it.second}" }
                    }"
                )
            }
            ApiResponse(message = "Assigned project(s) to user(s) successfully!", data = Unit)
        } catch (e: Exception) {
            ApiResponse(message = buildString {
                append("Couldn't complete work.")
                e.localizedMessage?.let { nnExceptionMsg ->
                    append(" Reason: $nnExceptionMsg")
                }
            })
        }
    }

    override fun checkIfUserLinkedProjectExists(
        projectId: String,
        userId: String
    ): Boolean {
        return userProjectRepository
            .existsByProjectIdAndUserId(
                projectId = projectId,
                userId = userId
            )
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

    override fun getAllUserIdsFromProjectId(projectId: String): List<String> {
        return userWorkRepository.getAllUserIdsForProjectId(projectId = projectId)
    }
}

fun DBUserProjectAssignment.toHarvestUserProject() = HarvestUserProjectAssignment(
    id = id,
    userId = userId,
    projectId = projectId
)

fun HarvestUserProjectAssignment.toDbUserProject() = DBUserProjectAssignment(
    userId = userId, projectId = projectId
).apply {
    this@toDbUserProject.id?.let { nnId ->
        this.id = nnId
    }
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