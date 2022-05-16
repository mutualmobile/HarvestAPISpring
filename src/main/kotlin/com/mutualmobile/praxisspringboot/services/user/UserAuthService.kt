package com.mutualmobile.praxisspringboot.services.user

import com.mutualmobile.praxisspringboot.data.user.DevicePlatform
import com.mutualmobile.praxisspringboot.data.user.RequestUser
import com.mutualmobile.praxisspringboot.entities.user.DBHarvestUser
import com.mutualmobile.praxisspringboot.data.models.auth.AuthResponse
import com.mutualmobile.praxisspringboot.data.models.auth.TokenRefreshRequest
import org.springframework.http.ResponseEntity
import javax.servlet.http.HttpServletRequest

interface UserAuthService {
    fun registerUser(requestUser: RequestUser?, resetPassword: Boolean = false): ResponseEntity<AuthResponse>
    fun loginUser(
        email: String?,
        password: String?,
        pushToken: String?,
        platform: DevicePlatform?
    ): ResponseEntity<AuthResponse>

    fun usernameExists(username: String?): DBHarvestUser?
    fun getLoggedInUser(token: String): ResponseEntity<RequestUser>
    fun refreshToken(tokenRefreshRequest: TokenRefreshRequest): ResponseEntity<AuthResponse>
    fun fcmToken(pushToken: String?, platform: DevicePlatform?, httpServletRequest: HttpServletRequest): AuthResponse
    fun getDbUser(token: String): DBHarvestUser?
    fun requestUser(token: String): RequestUser?
}