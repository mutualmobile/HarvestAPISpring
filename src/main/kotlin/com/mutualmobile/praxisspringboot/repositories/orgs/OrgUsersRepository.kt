package com.mutualmobile.praxisspringboot.repositories.orgs

import com.mutualmobile.praxisspringboot.entities.orgs.DBOrgUser
import org.springframework.data.jpa.repository.JpaRepository

interface OrgUsersRepository : JpaRepository<DBOrgUser, String>