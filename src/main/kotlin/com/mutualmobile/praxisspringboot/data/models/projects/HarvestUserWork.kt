package com.mutualmobile.praxisspringboot.data.models.projects

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

@JsonInclude(JsonInclude.Include.NON_NULL)
data class HarvestUserWork(
    @JsonProperty("id")
    val id: String? = null,
    @JsonProperty("user_project_id")
    val userProjectId: String,
    @JsonProperty("work_date")
    val workDate: Date,
    @JsonProperty("work_hours")
    val workHours: Int,
    @JsonProperty("note")
    val note: String? = null
)
