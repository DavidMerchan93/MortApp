package com.davidmerchan.domain.entities

data class Episode(
    val id: Int,
    val name: String,
    val url: String,
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
)