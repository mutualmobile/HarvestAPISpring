package com.mutualmobile.praxisspringboot.repositories.orgs

import com.mutualmobile.praxisspringboot.entities.orgs.DBOrganization
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface OrgRepository : JpaRepository<DBOrganization, String> {
    fun findAllByNameIgnoreCaseAndDeleted(name: String?, deleted: Boolean, page: Pageable): Page<DBOrganization>
    fun findAllByDeleted(deleted: Boolean, page: Pageable): Page<DBOrganization>
    fun findByNameIgnoreCaseAndDeleted(name: String, deleted: Boolean): DBOrganization?
    fun findByIdentifierIgnoreCaseAndDeleted(identifier: String, deleted: Boolean): DBOrganization?
}