package com.mutualmobile.praxisspringboot.services.orgs

import com.mutualmobile.praxisspringboot.data.ApiResponse

interface UserProjectService {
    fun assignProjectsToUsers(
        workList: HashMap<String, List<String>>
    ): ApiResponse<Unit>
}