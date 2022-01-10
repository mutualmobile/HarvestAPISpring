package com.mutualmobile.praxisspringboot.controllers.authuser

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.auth.ResetPasswordRequest
import com.mutualmobile.praxisspringboot.services.user.UserDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.servlet.view.RedirectView
import javax.servlet.http.HttpServletRequest

@Controller
class UserForgotPasswordApiImpl : UserForgotPasswordApi {

    @Autowired
    lateinit var userDataService: UserDataService

    override fun verifyEmail(token: String): RedirectView {
        return userDataService.verifyEmail(token)
    }

    override fun postForgotPassword(request: HttpServletRequest): ResponseEntity<ApiResponse<Void>?> {
        val email: String = request.getParameter(Endpoint.Params.EMAIL)
        return userDataService.postForgotPassword(email)
    }
    override fun postResetPassword(@RequestBody body: ResetPasswordRequest?): ResponseEntity<ApiResponse<Void>?> {
        val password: String? = body?.password
        val token: String? = body?.token
        return  userDataService.postResetPassword(token, password)
    }
}