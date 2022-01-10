package com.mutualmobile.praxisspringboot.controllers.authuser

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.auth.AuthResponse
import com.mutualmobile.praxisspringboot.data.models.auth.LogOutRequest
import com.mutualmobile.praxisspringboot.data.models.auth.RequestUserChangePassword
import com.mutualmobile.praxisspringboot.data.models.auth.TokenRefreshRequest
import com.mutualmobile.praxisspringboot.security.JwtTokenUtil
import com.mutualmobile.praxisspringboot.security.RefreshTokenService
import com.mutualmobile.praxisspringboot.services.user.UserAuthService
import com.mutualmobile.praxisspringboot.data.user.RequestUser
import com.mutualmobile.praxisspringboot.repositories.FCMRepository
import com.mutualmobile.praxisspringboot.services.user.UserDataService
import org.hibernate.internal.util.StringHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.transaction.Transactional

@RestController
class AuthApiImpl : AuthApi {
    @Autowired
    lateinit var userAuthService: UserAuthService

    @Autowired
    lateinit var userDataService: UserDataService

    @Autowired
    lateinit var refreshTokenService: RefreshTokenService

    @Autowired
    lateinit var fcmRepository: FCMRepository

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    override fun changePassword(
        httpServletRequest: HttpServletRequest,
        request: RequestUserChangePassword
    ): ResponseEntity<ApiResponse<Void>?> {
        val token = httpServletRequest.getToken()
        token?.let {
            val user = userAuthService.getDbUser(it)
            return userDataService.changePassword(request, user)
        }
        return ResponseEntity.badRequest().build()
    }

    override fun getUser(
        httpServletRequest: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<RequestUser> {
        val token = httpServletRequest.getToken()
        token?.let {
            return userAuthService.getLoggedInUser(it)
        }
        return ResponseEntity(null, HttpStatus.BAD_REQUEST)
    }

    override fun registerUser(body: RequestUser?): ResponseEntity<AuthResponse> {
        return userAuthService.registerUser(body, false)
    }

    @Transactional
    override fun logoutUser(@RequestBody logOutRequest: LogOutRequest, httpServletRequest: HttpServletRequest): ResponseEntity<*>? {
        val userId = jwtTokenUtil.getUserIdFromToken(httpServletRequest.getToken())
        userId?.let { refreshTokenService.deleteByUserId(userId) }
        logOutRequest.pushToken?.let { fcmRepository.deleteByToken(it) }
        return ResponseEntity(ApiResponse<String>("Log out successful!"), HttpStatus.OK)
    }

    override fun refreshToken(tokenRefreshRequest: TokenRefreshRequest): ResponseEntity<AuthResponse> {
        return userAuthService.refreshToken(tokenRefreshRequest)
    }

    override fun fcmToken(body: RequestUser?, httpServletRequest: HttpServletRequest): ResponseEntity<AuthResponse> {
        return if (body?.pushToken.isNullOrEmpty()) {
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        } else {
            ResponseEntity(userAuthService.fcmToken(body?.pushToken,body?.platform, httpServletRequest), HttpStatus.OK)
        }
    }

    override fun loginUser(body: RequestUser?): ResponseEntity<AuthResponse> {
        return if (body?.email.isNullOrEmpty() || body?.password.isNullOrEmpty()) {
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        } else {
            userAuthService.loginUser(body?.email, body?.password, body?.pushToken,body?.platform)
        }
    }

    override fun updateUser(
        body: RequestUser?,
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<ApiResponse<Void>?> {
        body?.let {
            return ResponseEntity(userDataService.updateUser(body, httpServletRequest), HttpStatus.OK)
        } ?: run {
            return ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }

}

fun HttpServletRequest.getToken(): String? {
    val header = this.getHeader(HttpHeaders.AUTHORIZATION)
    if (StringHelper.isEmpty(header) || !header.startsWith("Bearer ")) {
        return null
    }
    // Get jwt token and validate
    return header.split(" ").toTypedArray()[1].trim { it <= ' ' }
}