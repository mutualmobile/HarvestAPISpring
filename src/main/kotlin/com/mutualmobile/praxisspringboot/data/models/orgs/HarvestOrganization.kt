package com.mutualmobile.praxisspringboot.data.models.orgs

data class HarvestOrganization(
    val name: String,
    val website: String,
    val imgUrl: String,
    val id: String? = null,
    val identifier: String,
)//TODO add more fields to this request
