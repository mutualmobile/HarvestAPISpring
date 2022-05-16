package com.mutualmobile.praxisspringboot.security

enum class UserRole(val role: String) {
    ORG_ADMIN("1"),
    ORG_USER("2"),
    HARVEST_SUPER_ADMIN("3")
}

fun String.getUserRole(): UserRole {
    when (this) {
        UserRole.ORG_ADMIN.role -> {
            return UserRole.ORG_ADMIN
        }
        UserRole.ORG_USER.role -> {
            return UserRole.ORG_USER
        }
        UserRole.HARVEST_SUPER_ADMIN.role -> {
            return UserRole.HARVEST_SUPER_ADMIN
        }
    }
    throw RuntimeException("this role does not exist!")
}