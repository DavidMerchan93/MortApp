package com.davidmerchan.presentation.home.model

import com.davidmerchan.domain.entities.CharacterId

data class CharacterUiModel(
    val id: CharacterId,
    val name: String,
    val image: String,
    val status: String,
    val gender: String,
    val isFavorite: Boolean
)
