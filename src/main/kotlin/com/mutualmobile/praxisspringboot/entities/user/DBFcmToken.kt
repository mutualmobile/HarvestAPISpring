package com.mutualmobile.praxisspringboot.entities.user

import com.mutualmobile.praxisspringboot.data.user.DevicePlatform
import com.mutualmobile.praxisspringboot.entities.BaseEntity
import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "fcmtokens")
data class DBFcmToken(
    var userId: String,
    var token: String? = null,
    @Enumerated(EnumType.STRING)
    var platform: DevicePlatform?
) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as DBFcmToken

        return id == other.id
    }

    override fun hashCode(): Int = 1416657075

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , deleted = $deleted , createdTime = $createdTime , lastModifiedTime = $lastModifiedTime , userId = $userId , token = $token )"
    }
}