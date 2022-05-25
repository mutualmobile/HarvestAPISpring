package com.mutualmobile.praxisspringboot.services.user

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.auth.RequestUserChangePassword
import com.mutualmobile.praxisspringboot.data.user.RequestUser
import com.mutualmobile.praxisspringboot.entities.user.DBHarvestUser
import java.net.URL
import javax.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.servlet.view.RedirectView

interface UserDataService {
    fun updateUser(body: RequestUser?, httpServletRequest: HttpServletRequest): ApiResponse<Void>?
    fun changePassword(request: RequestUserChangePassword, user: DBHarvestUser?): ResponseEntity<ApiResponse<Void>?>
    fun postForgotPassword(email: String): ResponseEntity<ApiResponse<Void>?>
    fun postResetPassword(token: String?, newPassword: String?): ResponseEntity<ApiResponse<Void>?>
    fun getUser(httpServletRequest: HttpServletRequest): DBHarvestUser?
    fun getUsersOfType(offsetSafe: Int, limitSafe: Int, type: String?): ResponseEntity<ApiResponse<List<RequestUser>>>
    fun saveUserProfilePic(url: URL?, id: String)
    fun getUserByRole(userId: String, role: String): DBHarvestUser?
    fun verifyEmail(token: String?): RedirectView
    fun getUsersByTypeAndOrgName(userType: String, orgName: String?, isUserDeleted: Boolean, offset: Int?, limit: Int?): ApiResponse<List<RequestUser>>
}