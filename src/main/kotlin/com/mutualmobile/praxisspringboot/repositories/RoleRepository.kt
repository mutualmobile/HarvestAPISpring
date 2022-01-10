package com.mutualmobile.praxisspringboot.repositories

import com.mutualmobile.praxisspringboot.entities.user.DBRole
import org.springframework.data.repository.CrudRepository

interface RoleRepository : CrudRepository<DBRole, String> {
    fun findByUserId(username: String?): List<DBRole>
}