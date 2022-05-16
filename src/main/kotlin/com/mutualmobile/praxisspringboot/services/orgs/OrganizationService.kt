package com.mutualmobile.praxisspringboot.services.orgs

import com.mutualmobile.praxisspringboot.entities.orgs.DBOrganization
import org.springframework.data.domain.Page

interface OrganizationService {
    fun listOrganizations(offset: Int, limit: Int, search: String?): Page<DBOrganization>
}