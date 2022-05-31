package com.mutualmobile.praxisspringboot.repositories

import com.mutualmobile.praxisspringboot.entities.user.DBUserProjectAssignment
import org.springframework.data.jpa.repository.JpaRepository

interface UserProjectRepository: JpaRepository<DBUserProjectAssignment, String> {
    fun existsByProjectIdAndUserId(projectId: String, userId: String): Boolean
}