package com.davidmerchan.network.dto

import kotlinx.serialization.Serializable

/**
 * Character Data Transfer Object
 */
@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val image: String,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val location: LocationCharacterDto,
    val origin: LocationCharacterDto,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
)

/**
 * Location Character Data Transfer Object
 */
@Serializable
data class LocationCharacterDto(
    val name: String,
    val url: String,
)
