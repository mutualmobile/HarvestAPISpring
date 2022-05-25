package com.mutualmobile.praxisspringboot.repositories

import com.mutualmobile.praxisspringboot.entities.user.DBHarvestUser
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
        "SELECT * FROM praxisuser ksuser JOIN organizations ksorg ON ksuser.org_id = ksorg.id JOIN role ksrole ON ksuser.id = ksrole.user_id WHERE ksorg.identifier = :orgIdentifier AND ksrole.name = :type AND ksuser.deleted = :isUserDeleted OFFSET :offset LIMIT :limit",
        nativeQuery = true
    )
    fun findByTypeAndOrgName(
        @Param("type") type: String,
        @Param("orgIdentifier") orgIdentifier: String?,
        @Param("isUserDeleted") isUserDeleted: Boolean,
        @Param("offset") offsetSafe: Int?,
        @Param("limit") limitSafe: Int?,
        ): List<DBHarvestUser>

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