package com.mutualmobile.praxisspringboot.entities.user

import com.mutualmobile.praxisspringboot.entities.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user_project_assignment")
class DBUserProjectAssignment(
    val userId: String,
    val projectId: String
) : BaseEntity()
