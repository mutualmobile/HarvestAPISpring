package com.mutualmobile.praxisspringboot.entities.user

import com.mutualmobile.praxisspringboot.entities.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "praxisuser")
data class DBHarvestUser(
    var email: String? = null,
    var password: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var verified: Boolean? = false,
    @Column(name = "reset_password_token")
    var resetPasswordToken: String? = null,
    var avatarUrl: String? = null,
    val orgId: String,
    val projectIds: String? = null
) : BaseEntity() {
    fun name(): String {
        return "$firstName $lastName"
    }
}