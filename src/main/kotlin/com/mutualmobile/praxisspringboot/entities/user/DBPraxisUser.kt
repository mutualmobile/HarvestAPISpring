package com.mutualmobile.praxisspringboot.entities.user

import com.mutualmobile.praxisspringboot.entities.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "praxisuser")
data class DBPraxisUser(
    var email: String? = null,
    var password: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var verified: Boolean? = false,
    @Column(name = "reset_password_token")
    var resetPasswordToken: String? = null,
    var profilePic: String? = null
) : BaseEntity() {
    fun name(): String {
        return "$firstName $lastName"
    }
}