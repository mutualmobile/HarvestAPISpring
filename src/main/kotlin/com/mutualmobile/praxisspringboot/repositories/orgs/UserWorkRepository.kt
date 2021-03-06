package com.mutualmobile.praxisspringboot.repositories.orgs

import com.mutualmobile.praxisspringboot.entities.projects.DBUserWork
import java.util.Date
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserWorkRepository : JpaRepository<DBUserWork, String> {
    fun findAllByWorkDateBetweenAndUserId(startDate: Date, endDate: Date, userId: String): List<DBUserWork>

    // TODO : Move getAllProjectIdsForUserId & getAllUserIdsForProjectId to their respective Repository (they don't belong here)
    @Query(
        value = "SELECT upa.project_id FROM user_project_assignment upa WHERE upa.user_id = :userId",
        nativeQuery = true
    )
    fun getAllProjectIdsForUserId(userId: String): List<String> // List<ProjectId>
    @Query(
        value = "SELECT upa.user_id FROM user_project_assignment upa WHERE upa.project_id = :projectId",
        nativeQuery = true
    )
    fun getAllUserIdsForProjectId(projectId: String): List<String>
}