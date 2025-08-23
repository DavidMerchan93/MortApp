package com.davidmerchan.network.dto

data class CharacterDto(
    val id: Int,
    val name: String,
    val image: String,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val location: LocationDto,
    val origin: LocationDto,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)