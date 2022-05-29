package com.mutualmobile.praxisspringboot.controllers.authuser

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.auth.AuthResponse
import com.mutualmobile.praxisspringboot.data.models.auth.LogOutRequest
import com.mutualmobile.praxisspringboot.data.models.auth.RequestUserChangePassword
import com.mutualmobile.praxisspringboot.data.models.auth.TokenRefreshRequest
import com.mutualmobile.praxisspringboot.data.user.RequestUser
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

interface AuthApi {
    @PostMapping(Endpoint.FCM_TOKEN)
    fun fcmToken(@RequestBody body: RequestUser?, httpServletRequest: HttpServletRequest): ResponseEntity<AuthResponse>

    @PostMapping(Endpoint.SIGNUP)
    fun registerUser(@RequestBody body: RequestUser?): ResponseEntity<ApiResponse<RequestUser>>

    @PostMapping(Endpoint.LOGOUT)
    fun logoutUser(@RequestBody(required = false) logOutRequest: LogOutRequest?, httpServletRequest: HttpServletRequest): ResponseEntity<*>?

    @PostMapping(Endpoint.REFRESH_TOKEN)
    fun refreshToken(@RequestBody tokenRefreshRequest: TokenRefreshRequest): ResponseEntity<AuthResponse>

    @GetMapping(Endpoint.USER)
    fun getUser(httpServletRequest: HttpServletRequest, response: HttpServletResponse): ResponseEntity<RequestUser>

    @PostMapping(Endpoint.CHANGE_PASSWORD)
    fun changePassword(
        httpServletRequest: HttpServletRequest,
        @RequestBody request: RequestUserChangePassword
    ): ResponseEntity<ApiResponse<Void>?>

    @PostMapping(Endpoint.LOGIN)
    fun loginUser(
        @RequestBody body: RequestUser?
    ): ResponseEntity<AuthResponse>

    @PutMapping(Endpoint.USER)
    fun updateUser(@RequestBody body: RequestUser?, httpServletRequest: HttpServletRequest,): ResponseEntity<ApiResponse<Void>?>

    @PostMapping(Endpoint.ASSIGN_PROJECT)
    fun assignProjectToUser(
        @RequestParam projectId: String,
        @RequestParam userId: String
    ): ResponseEntity<ApiResponse<Void>>
}