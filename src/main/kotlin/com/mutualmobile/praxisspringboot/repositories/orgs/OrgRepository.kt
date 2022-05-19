package com.mutualmobile.praxisspringboot.repositories.orgs

import com.mutualmobile.praxisspringboot.entities.orgs.DBOrganization
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface OrgRepository : JpaRepository<DBOrganization, String> {
    fun findAllByName(name: String?, page: Pageable): Page<DBOrganization>
    fun findByName(name: String): DBOrganization?
    fun findByIdentifier(identifier: String): DBOrganization?
}