package com.mutualmobile.praxisspringboot.entities.orgs

import com.mutualmobile.praxisspringboot.entities.BaseEntity
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "project_user_entry")
data class DBOrgProjectUserEntry(
    val orgId: UUID,
    val userId: UUID,
    val notes: String,
    val time: Double,
    val workType: UUID,
) :
    BaseEntity()