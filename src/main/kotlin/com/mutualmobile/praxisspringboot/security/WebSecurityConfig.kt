package com.mutualmobile.praxisspringboot.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.controllers.Endpoint.UN_AUTH_API
import com.mutualmobile.praxisspringboot.services.user.PraxisUserService
import com.mutualmobile.praxisspringboot.util.Utility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var jwtAuthenticationTokenFilter: JwtRequestFilter

    @Autowired
    lateinit var userService: PraxisUserService

    @Autowired
    @Throws(Exception::class)
    fun configureAuth(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(userService)
            .passwordEncoder(passwordEncoderBean())
    }

    @Bean
    fun passwordEncoderBean(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Throws(java.lang.Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        // Enable CORS and disable CSRF
        var http = httpSecurity
        http = http.cors().and().csrf().disable()

        // Set session management to stateless
        http = http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

        // Set unauthorized requests exception handler
        http = http
            .exceptionHandling()
            .authenticationEntryPoint { request: HttpServletRequest?, response: HttpServletResponse, ex: AuthenticationException ->
                if (ex is InternalAuthenticationServiceException || ex is BadCredentialsException) {
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    response.status = HttpServletResponse.SC_UNAUTHORIZED
                    val body: MutableMap<String, Any> = HashMap()
                    body["message"] = "Email or password is incorrect! Please retry."
                    val mapper = ObjectMapper()
                    mapper.writeValue(response.outputStream, body)
                } else {
                    response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        ex.message
                    )
                }

            }
            .and()

        // Set permissions on endpoints
        http.authorizeRequests()
            .antMatchers(
                "/",
                "${UN_AUTH_API}/**",
                "${Endpoint.FORGOT_PASSWORD}/**",
                "${Endpoint.REFRESH_TOKEN}/**",
                "${Endpoint.EMAIL_VERIFY}/**",
                "${Endpoint.RESET_PASSWORD_ENDPOINT}/**",
                "${Endpoint.SIGNUP}/**",
                "${Endpoint.SERVICE_LOCATIONS}/**",
            )
            .permitAll()
            //user specific api's
            .antMatchers(
                HttpMethod.GET,
                "${Endpoint.USER}/**",
            )
            .hasAnyAuthority(UserRole.ADMIN.role, UserRole.SERVICE_PROVIDER.role, UserRole.CUSTOMER.role)
            .antMatchers(
                HttpMethod.POST,
                "${Endpoint.LOGOUT}/**",
                "${Endpoint.USER}/**",
                "${Endpoint.CHANGE_PASSWORD}/**"
            )
            .hasAnyAuthority(UserRole.ADMIN.role, UserRole.SERVICE_PROVIDER.role, UserRole.CUSTOMER.role)
            //notifications
            .antMatchers(HttpMethod.GET, "${Endpoint.NOTIFICATIONS}/**", "${Endpoint.NOTIFICATION_COUNT}/**")
            .hasAnyAuthority(UserRole.ADMIN.role, UserRole.SERVICE_PROVIDER.role, UserRole.CUSTOMER.role)
            .antMatchers(
                HttpMethod.GET,
                "${Endpoint.LIST_USERS}/**",
            )
            .hasAnyAuthority(UserRole.ADMIN.role)
            .anyRequest()
            .authenticated()

        // Add JWT token filter
        http.addFilterBefore(
            jwtAuthenticationTokenFilter,
            UsernamePasswordAuthenticationFilter::class.java
        )
    }

    // Used by spring security if CORS is enabled.
    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.allowedOrigins = listOf(
            "http://localhost:8080",
            "http://localhost:3000",
            Utility.getSiteURL(),
        )
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}
