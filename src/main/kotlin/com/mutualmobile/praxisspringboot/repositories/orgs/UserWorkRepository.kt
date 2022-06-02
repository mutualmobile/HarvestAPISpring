package com.mutualmobile.praxisspringboot.repositories.orgs

import com.mutualmobile.praxisspringboot.entities.projects.DBUserWork
import java.util.Date
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserWorkRepository : JpaRepository<DBUserWork, String> {
    fun findAllByWorkDateBetweenAndUserId(startDate: Date, endDate: Date, userId: String): List<DBUserWork>
    @Query(
        value = "SELECT uw.project_id FROM user_work uw WHERE uw.user_id = :userId",
        nativeQuery = true
    )
    fun getAllProjectIdsForUserId(userId: String): List<String> // List<ProjectId>
}