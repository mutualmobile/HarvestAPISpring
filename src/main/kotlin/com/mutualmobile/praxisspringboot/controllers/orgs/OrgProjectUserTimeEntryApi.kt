package com.mutualmobile.praxisspringboot.controllers.orgs

import com.mutualmobile.praxisspringboot.data.ApiResponse
import com.mutualmobile.praxisspringboot.data.models.orgs.TimeEntry
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam

interface OrgProjectUserTimeEntryApi {
    @GetMapping
    fun getTimeEntries(
        @RequestParam orgId: String?,
        @RequestParam projectId: String?
    ): ApiResponse<List<TimeEntry>>

    @PostMapping
    fun createTimeEntry(timeEntry: TimeEntry): ApiResponse<TimeEntry>

    @PutMapping
    fun updateTimeEntry(timeEntry: TimeEntry): ApiResponse<TimeEntry>
}