package com.mutualmobile.praxisspringboot.repositories.orgs

import com.mutualmobile.praxisspringboot.entities.orgs.DBOrgProjectUserEntry
import org.springframework.data.jpa.repository.JpaRepository

interface OrgTimeEntry : JpaRepository<DBOrgProjectUserEntry, String>