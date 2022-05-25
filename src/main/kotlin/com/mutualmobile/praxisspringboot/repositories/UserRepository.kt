package com.mutualmobile.praxisspringboot.repositories

import com.mutualmobile.praxisspringboot.entities.user.DBHarvestUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<DBHarvestUser, String> {
    fun findByResetPasswordToken(token: String?): DBHarvestUser?

    @Query(
        value = "SELECT * FROM praxisuser ksuser JOIN role ksrole on ksuser.id = ksrole.user_id where ksuser.email ILIKE concat('%', :email, '%') or ksuser.id ILIKE concat('%', :email, '%') ",
        nativeQuery = true
    )
    fun findByEmailOrId(@Param("email") email: String?): DBHarvestUser?

    @Query(
        "SELECT * FROM praxisuser ksuser JOIN organizations ksorg ON ksuser.org_id = ksorg.id JOIN role ksrole ON ksuser.id = ksrole.user_id WHERE ksorg.identifier = :orgIdentifier AND ksrole.name = :type AND ksuser.deleted = :isUserDeleted",
        nativeQuery = true
    )
    fun findByTypeAndOrgIdentifier(
        type: String,
        orgIdentifier: String?,
        isUserDeleted: Boolean,
        pageable: Pageable
        ): Page<DBHarvestUser>

    @Query(
        "select * FROM praxisuser ksuser JOIN role ksrole on ksuser.id = ksrole.user_id where ksrole.name like concat('%', :type, '%') offset :offset limit :limit",
        nativeQuery = true
    )
    fun findUsersOfType(
        @Param("offset") offsetSafe: Int,
        @Param("limit") limitSafe: Int,
        @Param("type") type: String
    ): List<DBHarvestUser>
}