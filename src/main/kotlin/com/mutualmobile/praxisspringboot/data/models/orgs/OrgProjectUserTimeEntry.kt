package com.mutualmobile.praxisspringboot.data.models.orgs

import com.mutualmobile.praxisspringboot.data.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam

interface OrgProjectUserTimeEntry {
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