package com.mutualmobile.praxisspringboot.entities.orgs

import com.mutualmobile.praxisspringboot.entities.BaseEntity
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "org_user")
data class DBOrgUser(
    val platformUserId: UUID,
    val orgId: String,
    val orgEmail: String,
    val avatarUrl: String
) :
    BaseEntity()