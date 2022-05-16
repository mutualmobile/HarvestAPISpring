package com.mutualmobile.praxisspringboot.entities.orgs.users

import com.mutualmobile.praxisspringboot.entities.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "org_user")
data class DBOrgUser(val fn: String, val ln: String, val orgId: String, val orgEmail: String, val avatarUrl: String) :
    BaseEntity()