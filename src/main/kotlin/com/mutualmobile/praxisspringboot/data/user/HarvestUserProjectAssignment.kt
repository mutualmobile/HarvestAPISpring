package com.mutualmobile.praxisspringboot.data.user

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class HarvestUserProjectAssignment(
    @JsonProperty("id")
    val id: String? = null,
    @JsonProperty("user_id")
    val userId: String,
    @JsonProperty("project_id")
    val projectId: String
)
