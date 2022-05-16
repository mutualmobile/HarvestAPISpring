package com.mutualmobile.praxisspringboot.services.user.impl

import com.mutualmobile.praxisspringboot.entities.user.DBRole
import com.mutualmobile.praxisspringboot.entities.user.DBHarvestUser
import com.mutualmobile.praxisspringboot.repositories.RoleRepository
import com.mutualmobile.praxisspringboot.repositories.UserRepository
import com.mutualmobile.praxisspringboot.services.user.PraxisUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.ArrayList

@Service
class PraxisUserServiceImpl : PraxisUserService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails? {
        try {
            val praxisUser: DBHarvestUser? = userRepository.findByEmailOrId(username!!)
            praxisUser?.let {
                val roles = roleRepository.findByUserId(praxisUser.id)
                val authorities = getAuthorities(roles)
                return User(praxisUser.id, praxisUser.password, authorities)
            } ?: run {
                throw UsernameNotFoundException("User not found!")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    private fun getAuthorities(
        roles: Collection<DBRole>
    ): List<GrantedAuthority?> {
        val authorities: MutableList<GrantedAuthority?> = ArrayList()
        for (role in roles) {
            authorities.add(SimpleGrantedAuthority(role.name))
        }
        return authorities
    }
}