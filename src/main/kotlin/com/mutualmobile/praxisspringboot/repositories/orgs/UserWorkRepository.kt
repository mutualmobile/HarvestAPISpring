package com.mutualmobile.praxisspringboot.repositories.orgs

import com.mutualmobile.praxisspringboot.entities.projects.DBUserWork
import java.util.Date
import org.springframework.data.jpa.repository.JpaRepository

interface UserWorkRepository : JpaRepository<DBUserWork, String> {
    fun findAllByWorkDateBetweenAndUserId(startDate: Date, endDate: Date, userId: String): List<DBUserWork>
}