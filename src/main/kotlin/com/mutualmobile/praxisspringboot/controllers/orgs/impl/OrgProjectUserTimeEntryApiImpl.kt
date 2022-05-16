package com.mutualmobile.praxisspringboot.controllers.orgs.impl

import com.mutualmobile.praxisspringboot.controllers.orgs.OrgProjectUserTimeEntryApi
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.TimeEntry

class OrgProjectUserTimeEntryApiImpl : OrgProjectUserTimeEntryApi {
    override fun getTimeEntries(orgId: String?, projectId: String?, userId: String?): ApiResponse<List<TimeEntry>> {
        TODO("Not yet implemented")
    }

    override fun createTimeEntry(timeEntry: TimeEntry): ApiResponse<TimeEntry> {
        TODO("Not yet implemented")
    }

    override fun updateTimeEntry(timeEntry: TimeEntry): ApiResponse<TimeEntry> {
        TODO("Not yet implemented")
    }
}