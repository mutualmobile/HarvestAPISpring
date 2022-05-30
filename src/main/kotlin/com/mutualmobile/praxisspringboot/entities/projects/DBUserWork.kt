package com.mutualmobile.praxisspringboot.entities.projects

import com.mutualmobile.praxisspringboot.entities.BaseEntity
import java.util.Date
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user_work")
class DBUserWork(
    // Used to connect this table to the user_project table
    val userProjectId: String,
    /** Describes on what date was this work performed */
    val workDate: Date,
    /** Describes how many hours did the user work on this task */
    val workHours: Int,
    /** Additional notes from user */
    val note: String? = null
): BaseEntity()