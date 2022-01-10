package com.mutualmobile.praxisspringboot.communication.email

data class PraxisNotification(
    val mailTo: List<String>,
    val subject: String,
    val attachments: List<Any>,
    val props: Map<String, Any>,
    val template:String
)