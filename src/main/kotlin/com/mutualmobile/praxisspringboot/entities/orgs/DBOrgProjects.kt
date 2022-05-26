package com.mutualmobile.praxisspringboot.entities.orgs

import com.mutualmobile.praxisspringboot.entities.BaseEntity
import java.util.Date
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "org_projects")
class DBOrgProjects(
    val name: String,
    val client: String,
    val startDate: Date?,
    val endDate: Date?,
    val isIndefinite: Boolean,
    val organizationId: String? = null,
) : BaseEntity()