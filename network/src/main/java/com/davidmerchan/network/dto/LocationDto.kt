package com.davidmerchan.network.dto

data class LocationDto(
    val id: Int,
    val name: String,
    val created: String,
    val dimension: String,
    val residents: List<String>,
    val type: String,
    val url: String
)