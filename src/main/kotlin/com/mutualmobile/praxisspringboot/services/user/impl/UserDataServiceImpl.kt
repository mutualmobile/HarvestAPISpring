package com.mutualmobile.praxisspringboot.services.user.impl

import com.mutualmobile.praxisspringboot.communication.email.PraxisNotification
import com.mutualmobile.praxisspringboot.communication.email.PraxisNotificationService
import com.mutualmobile.praxisspringboot.controllers.authuser.getToken
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.auth.RequestUserChangePassword
import com.mutualmobile.praxisspringboot.data.user.RequestUser
import com.mutualmobile.praxisspringboot.entities.user.DBHarvestUser
import com.mutualmobile.praxisspringboot.repositories.RoleRepository
import com.mutualmobile.praxisspringboot.repositories.UserRepository
import com.mutualmobile.praxisspringboot.security.jwt.JwtTokenUtil
import com.mutualmobile.praxisspringboot.services.user.PraxisUserService
import com.mutualmobile.praxisspringboot.services.user.UserDataService
import com.mutualmobile.praxisspringboot.util.Utility
import java.net.URL
import java.util.Date
import javax.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.servlet.view.RedirectView


@Service
class UserDataServiceImpl : UserDataService {

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var notificationService: PraxisNotificationService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var praxisUserService: PraxisUserService

    override fun getUser(httpServletRequest: HttpServletRequest): DBHarvestUser? {
        val username = jwtTokenUtil.getUserIdFromToken(httpServletRequest.getToken()!!)
        val userDetails: UserDetails? = praxisUserService.loadUserByUsername(username)
        return userRepository.findByEmailOrId(userDetails?.username)
    }

    override fun getUserByRole(userId: String, role: String): DBHarvestUser? {
        val user = userRepository.findById(userId).orElse(null)
        user?.let {
            val userRole = roleRepository.findByUserId(it.id).firstOrNull()
            if (userRole?.name == role) {
                return user
            }
        }
        return null
    }

    override fun saveUserProfilePic(url: URL?, id: String) {
        val user = userRepository.findById(id).orElse(null)
        user?.let {
            it.avatarUrl = url.toString()
            it.lastModifiedTime = Date()
            userRepository.save(user)
        }
    }

    override fun getUsersOfType(
        offsetSafe: Int,
        limitSafe: Int,
        type: String?
    ): ResponseEntity<ApiResponse<List<RequestUser>>> {
        val customers = userRepository.findUsersOfType(offsetSafe, limitSafe, type ?: "1")
        return ResponseEntity.ok(ApiResponse("Success!", customers.map { it.toRequestUser() }))
    }

    override fun postForgotPassword(email: String): ResponseEntity<ApiResponse<Void>?> {
        val dbHarvestUser: DBHarvestUser? = userRepository.findByEmailOrId(email)
        dbHarvestUser?.let {
            val token = jwtTokenUtil.generateJWTToken(email)
            dbHarvestUser.resetPasswordToken = token
            userRepository.save(dbHarvestUser)
            val resetPasswordLink = Utility.getSiteURL() + "/resetPassword?token=" + token
            sendEmail(email, resetPasswordLink, it.name())
            return ResponseEntity.ok(ApiResponse(message = "We have sent a reset password link to your email. Please check."))
        } ?: run {
            return ResponseEntity.ok(ApiResponse(message = "User Not Found."))
        }
    }

    override fun verifyEmail(token: String?): RedirectView {
        var response = "emailVerifyFailed"
        val user = userRepository.findByResetPasswordToken(token)
        user?.let {
            it.verified = true
            it.resetPasswordToken = null
            userRepository.save(it)
            response = "emailVerifySuccess"
        }
        return RedirectView().apply {
            url = Utility.getSiteURL() + "/${response}"
        }
    }

    override fun getUsersByTypeAndOrgId(
        userType: String,
        orgId: String?,
        isUserDeleted: Boolean,
        pageable: Pageable,
        search: String?,
    ): ApiResponse<Pair<Int, List<RequestUser>>> {
        return try {
            val result = search?.takeIf { it.isNotEmpty() }?.let {
                userRepository.findByTypeAndOrgIdAndSearch(
                    type = userType,
                    orgId = orgId,
                    isUserDeleted = isUserDeleted,
                    pageable = pageable, search
                ).map { it.toRequestUser() }
            } ?: run {
                userRepository.findByTypeAndOrgId(
                    type = userType,
                    orgId = orgId,
                    isUserDeleted = isUserDeleted,
                    pageable = pageable
                ).map { it.toRequestUser() }
            }
            ApiResponse(data = Pair(result.totalPages, result.content))
        } catch (e: Exception) {
            ApiResponse(message = e.localizedMessage ?: "Unexpected error occurred!")
        }
    }

    override fun getUserById(userId: String): RequestUser? {
        return userRepository.findByIdOrNull(userId)?.toRequestUser()
    }

    override fun getAllUsersById(userIds: List<String>): List<RequestUser> {
        return userRepository.findAllById(userIds).map { it.toRequestUser() }
    }

    override fun checkIfUserExists(userId: String): Boolean {
        return userRepository.existsById(userId)
    }

    override fun postResetPassword(token: String?, newPassword: String?): ResponseEntity<ApiResponse<Void>?> {
        if (token == null || newPassword == null) {
            return ResponseEntity.ok(ApiResponse("Failed to fetch params"))
        }
        val user = userRepository.findByResetPasswordToken(token)
        user?.verified = true
        return if (user == null) {
            ResponseEntity.ok(ApiResponse("Invalid Token"))
        } else {
            updatePassword(user, newPassword)
            ResponseEntity.ok(ApiResponse("You have successfully changed your password."))
        }
    }

    private fun updatePassword(praxisUser: DBHarvestUser, newPassword: String) {
        praxisUser.password = passwordEncoder.encode(newPassword)
        praxisUser.resetPasswordToken = null
        userRepository.save(praxisUser)
    }

    fun sendEmail(recipientEmail: String?, link: String, name: String) {
        notificationService.sendEmail(
            PraxisNotification(
                mailTo = listOf(recipientEmail!!),
                subject = "Reset Password",
                attachments = listOf(),
                props = hashMapOf<String, Any>().apply {
                    this["title"] = "Reset Password"
                    this["subtitle"] = ""
                    this["message"] = "Hi ${name},\n" +
                            "Please click the BUTTON below to reset your password."
                    this["action"] = "RESET PASSWORD"
                    this["link"] = link
                },
                template = "defaulttemplate"
            ),
        )
    }

    override fun changePassword(
        request: RequestUserChangePassword,
        user: DBHarvestUser?
    ): ResponseEntity<ApiResponse<Void>?> {
        user?.let {
            if (!passwordEncoder.matches(request.oldPass, it.password)) {
                return ResponseEntity(
                    ApiResponse(message = "Old password entered by you is incorrect!"),
                    HttpStatus.OK
                )
            }
            if (passwordEncoder.matches(request.password, it.password)) {
                return ResponseEntity(
                    ApiResponse(message = "Password is the same as old Password"),
                    HttpStatus.OK
                )
            }
            user.password = passwordEncoder.encode(request.password)
            userRepository.save(user)
            return ResponseEntity(ApiResponse(message = "Password Changed"), HttpStatus.OK)
        }
        return ResponseEntity(ApiResponse(message = "User Not Found!"), HttpStatus.OK)
    }

    override fun updateUser(body: RequestUser?, httpServletRequest: HttpServletRequest): ApiResponse<Void>? {
        val dbHarvestUser: DBHarvestUser? = getUser(httpServletRequest)
        dbHarvestUser?.let { updatedDbUser ->
            body?.firstName?.let {
                updatedDbUser.firstName = it
            }
            body?.lastName?.let {
                updatedDbUser.lastName = it
            }
            userRepository.save(updatedDbUser)
            return ApiResponse("User updated successfully!")
        }
        return ApiResponse("User Not Found!")
    }
}

fun DBHarvestUser.toRequestUser(): RequestUser {
    return RequestUser(
        id = this.id,
        firstName = this.firstName?.trim(),
        lastName = this.lastName?.trim(),
        email = this.email?.trim(),
        password = null,
        profilePic = this.avatarUrl,
        modifiedTime = this.lastModifiedTime?.toString(),
        orgId = this.orgId,
    )
}

fun RequestUser?.toDBUser(): DBHarvestUser {
    return DBHarvestUser(
        email = this?.email?.trim(),
        password = this?.password,
        firstName = this?.firstName?.trim(),
        lastName = this?.lastName?.trim(),
        avatarUrl = this?.profilePic,
        orgId = this?.orgId!!,
    )
}