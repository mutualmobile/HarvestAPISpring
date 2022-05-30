package com.mutualmobile.praxisspringboot.repositories.orgs

import com.mutualmobile.praxisspringboot.entities.projects.DBUserWork
import org.springframework.data.jpa.repository.JpaRepository

interface UserWorkRepository : JpaRepository<DBUserWork, String>