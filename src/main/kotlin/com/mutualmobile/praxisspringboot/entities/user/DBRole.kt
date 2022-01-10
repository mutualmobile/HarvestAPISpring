package com.mutualmobile.praxisspringboot.entities.user

import com.mutualmobile.praxisspringboot.entities.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "role")
data class DBRole(
    val name: String? = null,
    val userId: String? = null,
) : BaseEntity()