package com.mutualmobile.praxisspringboot.services.orgs.impl

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.projects.HarvestUserWork
import com.mutualmobile.praxisspringboot.repositories.orgs.UserWorkRepository
import com.mutualmobile.praxisspringboot.services.orgs.UserWorkService
import java.util.Date
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserWorkServiceImpl : UserWorkService {
    @Autowired
    lateinit var userWorkRepository: UserWorkRepository

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
}