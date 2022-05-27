package com.mutualmobile.praxisspringboot.services.user.impl

import com.mutualmobile.praxisspringboot.communication.email.PraxisNotification
import com.mutualmobile.praxisspringboot.communication.email.PraxisNotificationService
import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.controllers.authuser.getToken
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.auth.AuthResponse
import com.mutualmobile.praxisspringboot.data.models.auth.TokenRefreshRequest
import com.mutualmobile.praxisspringboot.data.models.orgs.HarvestOrganization
import com.mutualmobile.praxisspringboot.data.user.DevicePlatform
import com.mutualmobile.praxisspringboot.data.user.RequestUser
import com.mutualmobile.praxisspringboot.entities.user.DBFcmToken
import com.mutualmobile.praxisspringboot.entities.user.DBRole
import com.mutualmobile.praxisspringboot.entities.user.DBHarvestUser
import com.mutualmobile.praxisspringboot.security.jwt.JwtTokenUtil
import com.mutualmobile.praxisspringboot.security.RefreshTokenService
import com.mutualmobile.praxisspringboot.repositories.FCMRepository
import com.mutualmobile.praxisspringboot.repositories.RoleRepository
import com.mutualmobile.praxisspringboot.repositories.UserRepository
import com.mutualmobile.praxisspringboot.repositories.orgs.OrgRepository
import com.mutualmobile.praxisspringboot.security.UserRole
import com.mutualmobile.praxisspringboot.services.orgs.OrganizationService
import com.mutualmobile.praxisspringboot.services.orgs.impl.toHarvestOrg
import com.mutualmobile.praxisspringboot.services.user.PraxisUserService
import com.mutualmobile.praxisspringboot.services.user.UserAuthService
import com.mutualmobile.praxisspringboot.util.Utility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class UserAuthServiceImpl : UserAuthService {
    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var orgRepository: OrgRepository

    @Autowired
    lateinit var organizationService: OrganizationService

    @Autowired
    lateinit var refreshTokenService: RefreshTokenService

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var praxisUserService: PraxisUserService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var fcmRepository: FCMRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var notificationService: PraxisNotificationService


    override fun usernameExists(username: String?): DBHarvestUser? {
        return userRepository.findByEmailOrId(username!!)
    }

    override fun refreshToken(tokenRefreshRequest: TokenRefreshRequest): ResponseEntity<AuthResponse> {
        val requestRefreshToken = tokenRefreshRequest.refreshToken
        val refreshToken = refreshTokenService.findByToken(requestRefreshToken)
        refreshToken?.let {
            val userId = refreshTokenService.verifyExpiration(refreshToken).userid
            userId?.let {
                val token = jwtTokenUtil.generateJWTToken(userId)
                return ResponseEntity.ok(AuthResponse(token, null, requestRefreshToken!!))
            }
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        } ?: run {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    fun sendVerifyEmail(recipientEmail: String?, link: String, name: String) {
        notificationService.sendEmail(
            PraxisNotification(
                mailTo = listOf(recipientEmail!!),
                subject = "Please verify your email",
                attachments = listOf(),
                props = hashMapOf<String, Any>().apply {
                    this["title"] = "Verify Email"
                    this["subtitle"] = "To access the app\nVerify your Email."
                    this["message"] = "Hi ${name},\n" +
                            "Please verify your email to log into your account"
                    this["action"] = "VERIFY EMAIL"
                    this["link"] = link
                },
                template = "defaulttemplate"
            ),
        )
    }

    override fun registerUser(
        requestUser: RequestUser?,
        resetPassword: Boolean
    ): ResponseEntity<ApiResponse<RequestUser>> {
        requestUser?.let {
            ifUserExists(requestUser)?.let {
                return it
            }
            return registerUserInternal(requestUser, resetPassword)
        } ?: run {
            return ResponseEntity(
                ApiResponse(message = "Request body is null!"),
                HttpStatus.BAD_REQUEST
            )
        }
    }

    private fun registerUserInternal(
        requestUser: RequestUser,
        resetPassword: Boolean
    ): ResponseEntity<ApiResponse<RequestUser>> {
        val organization = orgRepository.findByIdOrNull(requestUser.orgId ?: "-1")
        organization?.let {
            return registerWhenOrganizationAvailable(requestUser, resetPassword, it.toHarvestOrg())
        } ?: run {
            return registerWhenNewOrganization(requestUser, resetPassword)
        }
    }

    private fun registerWhenNewOrganization(
        requestUser: RequestUser,
        resetPassword: Boolean
    ): ResponseEntity<ApiResponse<RequestUser>> {
        requestUser.harvestOrganization?.let { harvestOrganization ->
            return tryWhenNewOrganization(harvestOrganization, requestUser, resetPassword)
        } ?: run {
            return ResponseEntity.ok(
                ApiResponse(
                    data = null,
                    message = "Please share the organization details in the request"
                )
            )
        }
    }

    private fun tryWhenNewOrganization(
        harvestOrganization: HarvestOrganization,
        requestUser: RequestUser,
        resetPassword: Boolean
    ): ResponseEntity<ApiResponse<RequestUser>> {
        val existingOrg = orgRepository.findByIdentifierIgnoreCaseAndDeleted(harvestOrganization.identifier!!, deleted = false)
        existingOrg?.let {
            return ResponseEntity.ok(
                ApiResponse(
                    data = null,
                    message = "The organization already exists, please search and select."
                )
            )
        } ?: run {
            val org = organizationService.createOrganization(harvestOrganization)
            requestUser.role = UserRole.ORG_ADMIN.role // this makes sure the user is an org-admin
            requestUser.orgId = org.id
            return registerWhenOrganizationAvailable(requestUser, resetPassword, org)
        }
    }

    private fun registerWhenOrganizationAvailable(
        requestUser: RequestUser,
        resetPassword: Boolean,
        dbOrganization: HarvestOrganization
    ): ResponseEntity<ApiResponse<RequestUser>> {
        val dbUser = saveUser(requestUser, resetPassword)
        defineRoleAndType(requestUser, dbUser)
        return ResponseEntity.ok(
            ApiResponse(
                data = dbUser.toRequestUser().copy(harvestOrganization = dbOrganization, orgId = dbOrganization.id),
                message = "Registration Successful! Please verify your email before getting started!"
            )
        )
    }

    private fun verificationEmail(token: String?, dbUser: DBHarvestUser) {
        val verification = Utility.getApiBaseURL() + "${Endpoint.EMAIL_VERIFY}?${Endpoint.Params.TOKEN}=" + token;
        sendVerifyEmail(dbUser.email, verification, dbUser.name())
    }

    private fun ifUserExists(requestUser: RequestUser): ResponseEntity<ApiResponse<RequestUser>>? {
        if (usernameExists(requestUser.email) != null) {
            return ResponseEntity(
                ApiResponse(message = "An account with this email address already exist"),
                HttpStatus.BAD_REQUEST
            )
        }
        return null
    }

    private fun saveUser(requestUser: RequestUser?, resetPassword: Boolean): DBHarvestUser {
        requestUser?.password = passwordEncoder.encode(requestUser?.password)
        val token = jwtTokenUtil.generateJWTToken(requestUser?.email!!)
        val dbUser = requestUser.toDBUser()
        dbUser.resetPasswordToken = token
        userRepository.save(dbUser)
        if (resetPassword) {
            val resetPasswordLink = Utility.getSiteURL() + "/resetPassword?token=" + token;
            sendVerifyEmail(requestUser.email, resetPasswordLink, dbUser.name())
        } else {
            verificationEmail(token, dbUser)
        }

        return dbUser
    }

    private fun defineRoleAndType(
        requestUser: RequestUser,
        dbUser: DBHarvestUser
    ) {
        roleRepository.save(DBRole(requestUser.role, dbUser.id))
    }


    override fun loginUser(
        email: String?,
        password: String?,
        pushToken: String?,
        platform: DevicePlatform?
    ): ResponseEntity<AuthResponse> {
        val authenticate = authenticationManager
            .authenticate(UsernamePasswordAuthenticationToken(email, password))
        SecurityContextHolder.getContext().authentication = authenticate
        val user: User = authenticate.principal as User
        val dbUser = userRepository.findByEmailOrId(email!!)
        dbUser?.let {
            return when (dbUser.verified) {
                true -> {
                    loginWhenVerified(dbUser, pushToken, platform)
                }
                else -> {
                    loginWhenNotVerified(dbUser, user)
                }
            }
        }
        return ResponseEntity.ok(AuthResponse("", "User does not exist in the system", ""))
    }

    private fun loginWhenNotVerified(
        dbUser: DBHarvestUser,
        user: User
    ): ResponseEntity<AuthResponse> {
        try {
            val tokenExpDate = jwtTokenUtil.getExpirationDateFromToken(dbUser.resetPasswordToken)
            if (tokenExpDate == null || jwtTokenUtil.isTokenExpired(dbUser.resetPasswordToken)) {
                val token = jwtTokenUtil.generateJWTToken(user.username)
                dbUser.resetPasswordToken = token
                userRepository.save(dbUser)
                verificationEmail(token, dbUser)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return ResponseEntity.ok(AuthResponse("", "Please verify your email, check your inbox!", ""))
    }

    private fun loginWhenVerified(
        dbUser: DBHarvestUser,
        pushToken: String?,
        platform: DevicePlatform?
    ): ResponseEntity<AuthResponse> {
        val token = jwtTokenUtil.generateJWTToken(dbUser.id)
        val refreshToken = refreshTokenService.createRefreshToken(dbUser.id)
        pushToken?.let {
            userForToken(pushToken, dbUser.id, platform)
        }
        return ResponseEntity.ok(AuthResponse(token, "User logged in Successfully", refreshToken.token))
    }

    override fun getLoggedInUser(token: String): ResponseEntity<RequestUser> {
        val resUser = requestUser(token)
        return ResponseEntity.ok(resUser)
    }

    override fun requestUser(token: String): RequestUser? {
        val username = jwtTokenUtil.getUserIdFromToken(token)
        val userDetails: UserDetails? = praxisUserService.loadUserByUsername(username)
        val user = userRepository.findByEmailOrId(userDetails?.username!!)
        val resUser = user?.toRequestUser()
        val role = roleRepository.findByUserId(user?.id)
        resUser?.role = role.first().name
        return resUser
    }

    override fun getDbUser(token: String): DBHarvestUser? {
        val username = jwtTokenUtil.getUserIdFromToken(token)
        val userDetails: UserDetails? = praxisUserService.loadUserByUsername(username)
        return userRepository.findByEmailOrId(userDetails?.username!!)
    }

    override fun fcmToken(
        pushToken: String?,
        platform: DevicePlatform?,
        httpServletRequest: HttpServletRequest
    ): AuthResponse {
        val loggedInUser = jwtTokenUtil.getUserIdFromToken(httpServletRequest.getToken())
        return userForToken(pushToken, loggedInUser, platform)
    }

    private fun userForToken(
        pushToken: String?,
        userId: String?,
        platform: DevicePlatform?
    ): AuthResponse {
        pushToken?.let {
            val dbToken = fcmRepository.findByToken(it)
            dbToken?.let {
                // token already exists
                it.userId = userId!!
                it.platform = platform
                fcmRepository.save(it)
            } ?: kotlin.run {
                fcmRepository.save(DBFcmToken(userId = userId!!, token = pushToken, platform = platform))
            }
            return AuthResponse("", "Token updated in Successfully", "")
        }
        return AuthResponse("", "Push token not provided in body", "")

    }
}