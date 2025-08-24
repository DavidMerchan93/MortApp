package com.davidmerchan.presentation.detail.model

import com.davidmerchan.domain.entities.CharacterId

data class CharacterDetailUiModel(
    val id: CharacterId,
    val name: String,
    val image: String,
    val status: String,
    val gender: String,
    val location: String,
    val origin: String,
    val species: String,
    val isFavorite: Boolean,
)
