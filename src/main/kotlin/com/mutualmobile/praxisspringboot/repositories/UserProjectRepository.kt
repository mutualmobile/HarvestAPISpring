package com.mutualmobile.praxisspringboot.repositories

import com.mutualmobile.praxisspringboot.entities.user.DBUserProject
import org.springframework.data.jpa.repository.JpaRepository

interface UserProjectRepository: JpaRepository<DBUserProject, String> {
    fun findByProjectIdAndUserId(projectId: String, userId: String): DBUserProject?
}