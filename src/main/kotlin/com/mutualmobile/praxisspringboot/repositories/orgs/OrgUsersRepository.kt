package com.mutualmobile.praxisspringboot.repositories.orgs

import org.springframework.data.jpa.repository.JpaRepository

interface OrgUsersRepository : JpaRepository<DBOrgUser, String>