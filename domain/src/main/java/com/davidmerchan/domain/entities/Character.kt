package com.davidmerchan.domain.entities

data class Character(
    val id: Int,
    val name: String,
    val image: String,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val location: Location,
    val origin: Location,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)