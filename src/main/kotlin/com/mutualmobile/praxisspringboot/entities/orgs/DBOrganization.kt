package com.mutualmobile.praxisspringboot.entities.orgs

import com.mutualmobile.praxisspringboot.entities.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "orgs")
data class DBOrganization(val name: String, val website: String, val imgUrl: String) : BaseEntity()
