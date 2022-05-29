package com.mutualmobile.praxisspringboot.entities.user

import com.mutualmobile.praxisspringboot.entities.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user_project")
class DBUserProject(
    val userId: String,
    val projectId: String
): BaseEntity()
