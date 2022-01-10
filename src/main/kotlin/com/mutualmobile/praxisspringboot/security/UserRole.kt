package com.mutualmobile.praxisspringboot.security

enum class UserRole(val role: String) {
    SERVICE_PROVIDER("1"),
    CUSTOMER("2"),
    ADMIN("3")
}

fun String.getUserRole(): UserRole {
    when (this) {
        UserRole.SERVICE_PROVIDER.role -> {
            return UserRole.SERVICE_PROVIDER
        }
        UserRole.CUSTOMER.role -> {
            return UserRole.CUSTOMER
        }
        UserRole.ADMIN.role -> {
            return UserRole.ADMIN
        }
    }
    throw RuntimeException()
}