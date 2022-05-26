package com.mutualmobile.praxisspringboot.security

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.controllers.Endpoint.ORGANIZATION
import com.mutualmobile.praxisspringboot.controllers.Endpoint.TIME_ENTRIES
import com.mutualmobile.praxisspringboot.controllers.Endpoint.UN_AUTH_API
import com.mutualmobile.praxisspringboot.security.jwt.JwtRequestFilter
import com.mutualmobile.praxisspringboot.services.user.PraxisUserService
import com.mutualmobile.praxisspringboot.util.Utility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


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
    lateinit var praxisAuthEntryPoint: PraxisAuthEntryPoint

    @Autowired
    lateinit var passwordEncoderBean: PasswordEncoder

    @Autowired
    @Throws(Exception::class)
    fun configureAuth(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(userService)
            .passwordEncoder(passwordEncoderBean)
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
            .authenticationEntryPoint(praxisAuthEntryPoint)
            .and()

        // Set permissions on endpoints
        http.authorizeRequests()
            .antMatchers(
                "/",
                "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
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
            .hasAnyAuthority(UserRole.HARVEST_SUPER_ADMIN.role, UserRole.ORG_ADMIN.role, UserRole.ORG_USER.role)
            .antMatchers(
                HttpMethod.POST,
                "${Endpoint.LOGOUT}/**",
                "${Endpoint.USER}/**",
                "${Endpoint.CHANGE_PASSWORD}/**"
            )
            .hasAnyAuthority(UserRole.HARVEST_SUPER_ADMIN.role, UserRole.ORG_ADMIN.role, UserRole.ORG_USER.role)
            //notifications
            .antMatchers(HttpMethod.GET, "${Endpoint.NOTIFICATIONS}/**", "${Endpoint.NOTIFICATION_COUNT}/**")
            .hasAnyAuthority(UserRole.HARVEST_SUPER_ADMIN.role, UserRole.ORG_ADMIN.role, UserRole.ORG_USER.role)
            // get time entries enabled for any user role
            .antMatchers(HttpMethod.GET, "${TIME_ENTRIES}/**")
            .hasAnyAuthority(
                UserRole.HARVEST_SUPER_ADMIN.role, UserRole.ORG_ADMIN.role, UserRole.ORG_USER.role
            )
            .antMatchers(HttpMethod.POST,"${ORGANIZATION}/**")
            .hasAnyAuthority(
                 UserRole.ORG_ADMIN.role
            )
            .antMatchers(HttpMethod.GET,"${ORGANIZATION}/**")
            .hasAnyAuthority(
                UserRole.ORG_ADMIN.role,
                UserRole.ORG_USER.role,
                UserRole.HARVEST_SUPER_ADMIN.role,
            )
            .antMatchers(HttpMethod.PUT,"${ORGANIZATION}/**")
            .hasAnyAuthority(
                UserRole.ORG_ADMIN.role
            )
            .antMatchers(
                HttpMethod.GET,
                "${Endpoint.LIST_USERS}/**",
            )
            .hasAnyAuthority(UserRole.HARVEST_SUPER_ADMIN.role)
            .anyRequest()
            .authenticated()

        // Add JWT token filter
        http.addFilterBefore(
            jwtAuthenticationTokenFilter,
            UsernamePasswordAuthenticationFilter::class.java
        )
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/h2-console/**",
            "/v3/api-docs",
            "/swagger-ui.html",
            "/swagger-ui/**",
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
