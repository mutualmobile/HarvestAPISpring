package com.mutualmobile.praxisspringboot.entities.user

import com.mutualmobile.praxisspringboot.entities.BaseEntity
import org.hibernate.Hibernate
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity(name = "refreshtoken")
data class DBRefreshToken(
    var userid: String? = null,

    @Column(nullable = false, unique = true)
    var token: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    var expiryDate: Instant? = null
) : BaseEntity()