package com.mutualmobile.praxisspringboot.entities

import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class BaseEntity(
    @Column(name = "deleted")
    var deleted: Boolean = false,
    @Id
    @Column(name = "id", nullable = false)
    var id: String = UUID.randomUUID().toString(),

    @Column(name = "createdTime", nullable = false)
    var createdTime: Date? = Date(),

    @Column(name = "modifiedTime")
    var lastModifiedTime: Date? = Date()
) {
    @PrePersist
    fun prePersist() {
        if (createdTime == null) createdTime = Date()
        if (lastModifiedTime == null) lastModifiedTime = Date()
    }

    @PreUpdate
    fun preUpdate() {
        lastModifiedTime = Date()
    }

    @PreRemove
    fun preRemove() {
        lastModifiedTime = Date()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as BaseEntity

        return id == other.id
    }

    override fun hashCode(): Int = 699169739

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , deleted = $deleted , createdTime = $createdTime , lastModifiedTime = $lastModifiedTime )"
    }
}