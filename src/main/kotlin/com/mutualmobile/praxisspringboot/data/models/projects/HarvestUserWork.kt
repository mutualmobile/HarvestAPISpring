package com.mutualmobile.praxisspringboot.data.models.projects

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.Date

@JsonInclude(JsonInclude.Include.NON_NULL)
data class HarvestUserWork(
    val id: String? = null,
    val projectId: String,
    val userId: String,
    val workDate: Date,
    val workHours: Float,
    val workType: String,
    val note: String? = null
)



enum class WorkType(val type: String) {
    BILLABLE("1"),
    NONBILLABLE("2")
}
