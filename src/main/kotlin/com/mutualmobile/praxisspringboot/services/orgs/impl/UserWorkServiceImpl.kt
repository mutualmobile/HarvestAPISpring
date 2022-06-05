package com.mutualmobile.praxisspringboot.services.orgs.impl

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationProject
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork
import com.mutualmobile.praxisspringboot.repositories.orgs.OrgProjectsRepository
import com.mutualmobile.praxisspringboot.repositories.orgs.UserWorkRepository
import com.mutualmobile.praxisspringboot.services.orgs.UserWorkService
import java.util.Date
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserWorkServiceImpl : UserWorkService {
    @Autowired
    lateinit var userWorkRepository: UserWorkRepository

    @Autowired
    lateinit var orgProjectsRepository: OrgProjectsRepository

    override fun getWorkLogsForDateRange(
        startDate: Date,
        endDate: Date,
        userIds: List<String>?
    ): ApiResponse<List<HarvestUserWork>> {
        return try {
            val listOfWork = mutableListOf<HarvestUserWork>()
            userIds?.forEach { userId ->
                listOfWork.addAll(
                    userWorkRepository.findAllByWorkDateBetweenAndUserId(
                        startDate = startDate,
                        endDate = endDate,
                        userId = userId
                    ).map { it.toHarvestUserWork() }
                )
            }
            if (listOfWork.isEmpty()) throw Exception("No entries found for the given details!")
            ApiResponse(data = listOfWork)
        } catch (e: Exception) {
            ApiResponse(message = e.localizedMessage)
        }
    }

    override fun getUserAssignedProjects(userId: String): ApiResponse<List<OrganizationProject>> {
        return try {
            val allProjectIds = userWorkRepository.getAllProjectIdsForUserId(userId = userId)
            if (allProjectIds.isEmpty()) throw Exception("No project ID(s) found for the given userId!")

            val allProjects = orgProjectsRepository.findAllById(allProjectIds)
            if (allProjects.isEmpty()) throw Exception("No project(s) found for the given projectId(s)!")

            ApiResponse(data = allProjects.map { it.toOrgProject() })
        } catch (e: Exception) {
            ApiResponse(message = buildString {
                append("Couldn't fetch projects.")

                e.localizedMessage?.let { nnExceptionMsg ->
                    append(" Reason: $nnExceptionMsg")
                }
            })
        }
    }
}