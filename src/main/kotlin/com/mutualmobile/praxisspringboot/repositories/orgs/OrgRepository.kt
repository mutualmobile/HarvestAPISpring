package com.mutualmobile.praxisspringboot.repositories.orgs

import com.mutualmobile.praxisspringboot.entities.orgs.DBOrganization
import org.springframework.data.jpa.repository.JpaRepository

interface OrgRepository : JpaRepository<DBOrganization, String>