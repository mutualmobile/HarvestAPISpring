package com.mutualmobile.praxisspringboot.controllers.authuser

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.auth.ResetPasswordRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.view.RedirectView
import javax.servlet.http.HttpServletRequest

interface UserForgotPasswordApi {
    @GetMapping(Endpoint.EMAIL_VERIFY)
    fun verifyEmail(@RequestParam(Endpoint.Params.TOKEN) token:String): RedirectView

    @PostMapping(Endpoint.RESET_PASSWORD_ENDPOINT)
    fun postResetPassword(@RequestBody body: ResetPasswordRequest?): ResponseEntity<ApiResponse<Void>?>

    @PostMapping(Endpoint.FORGOT_PASSWORD)
    fun postForgotPassword(
        request: HttpServletRequest
    ): ResponseEntity<ApiResponse<Void>?>
}
