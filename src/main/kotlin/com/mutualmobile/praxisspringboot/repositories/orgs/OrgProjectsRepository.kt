package com.mutualmobile.praxisspringboot.repositories.orgs

import com.mutualmobile.praxisspringboot.entities.orgs.DBOrgProjects
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface OrgProjectsRepository : JpaRepository<DBOrgProjects, String> {
    fun findAllByOrganizationIdAndNameLike(
        organizationId: String,
        name: String,
        pageable: Pageable
    ): Page<DBOrgProjects>

    fun findAllByOrganizationId(
        organizationId: String,
        pageable: Pageable
    ): Page<DBOrgProjects>
}