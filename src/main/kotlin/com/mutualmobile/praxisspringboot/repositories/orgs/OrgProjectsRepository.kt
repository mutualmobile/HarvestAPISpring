package com.mutualmobile.praxisspringboot.repositories.orgs

import com.mutualmobile.praxisspringboot.entities.orgs.DBOrgProjects
import org.springframework.data.jpa.repository.JpaRepository

interface OrgProjectsRepository : JpaRepository<DBOrgProjects, String>