package com.mutualmobile.praxisspringboot.services.orgs.impl

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.user.HarvestUserProject
import com.mutualmobile.praxisspringboot.entities.user.DBUserProject
import com.mutualmobile.praxisspringboot.repositories.UserProjectRepository
import com.mutualmobile.praxisspringboot.services.orgs.UserProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UserProjectServiceImpl : UserProjectService {
    @Autowired
    lateinit var userProjectRepository: UserProjectRepository

    override fun assignProjectToUser(projectId: String, userId: String): ResponseEntity<ApiResponse<HarvestUserProject>> {
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