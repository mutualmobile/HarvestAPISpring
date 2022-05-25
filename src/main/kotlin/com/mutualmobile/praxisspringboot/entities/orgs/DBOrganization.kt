package com.mutualmobile.praxisspringboot.entities.orgs

import com.mutualmobile.praxisspringboot.entities.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "organizations")
data class DBOrganization(@Column(unique = true) val name: String, val website: String, val imgUrl: String, val identifier: String) :
    BaseEntity()
