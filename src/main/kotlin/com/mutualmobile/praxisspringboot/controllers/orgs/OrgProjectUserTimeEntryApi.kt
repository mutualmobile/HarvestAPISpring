package com.mutualmobile.praxisspringboot.controllers.orgs

import com.mutualmobile.praxisspringboot.controllers.Endpoint
import com.mutualmobile.praxisspringboot.controllers.Endpoint.TIME_ENTRY
import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.TimeEntry
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam

interface OrgProjectUserTimeEntryApi {
    @GetMapping(Endpoint.TIME_ENTRIES)
    fun getTimeEntries(
        @RequestParam orgId: String?,
        @RequestParam projectId: String?,
        @RequestParam userId: String?
    ): ApiResponse<List<TimeEntry>>

    @PostMapping(Endpoint.TIME_ENTRY)
    fun createTimeEntry(timeEntry: TimeEntry): ApiResponse<TimeEntry>

    @PutMapping(TIME_ENTRY)
    fun updateTimeEntry(timeEntry: TimeEntry): ApiResponse<TimeEntry>
}