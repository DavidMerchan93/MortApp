package com.davidmerchan.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val id: Int? = null,
    val name: String? = null,
    val created: String? = null,
    val dimension: String? = null,
    val residents: List<String>? = emptyList(),
    val type: String? = null,
    val url: String? = null,
)
