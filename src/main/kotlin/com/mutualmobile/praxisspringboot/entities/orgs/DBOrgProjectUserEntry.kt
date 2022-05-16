package com.mutualmobile.praxisspringboot.entities.orgs

import com.mutualmobile.praxisspringboot.entities.BaseEntity
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "project_user_entry")
data class DBOrgProjectUserEntry(val userId: UUID, val notes: String, val time: Double, val workType: UUID) :
    BaseEntity()


@Entity
@Table(name = "org_work_type")
data class DBOrgWorkType(val title: String) : BaseEntity() // can be billable, non-billable, anything-custom etc..

