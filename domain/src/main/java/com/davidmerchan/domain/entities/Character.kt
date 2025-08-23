package com.davidmerchan.domain.entities

typealias CharacterId = Int

data class Character(
    val id: CharacterId,
    val name: String,
    val image: String,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val location: LocationCharacter,
    val origin: LocationCharacter,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
    val isFavorite: Boolean = false
)

data class LocationCharacter(
    val name: String,
    val url: String
)