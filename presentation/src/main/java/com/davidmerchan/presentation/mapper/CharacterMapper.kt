package com.davidmerchan.presentation.mapper

import com.davidmerchan.domain.entities.Character
import com.davidmerchan.presentation.detail.model.CharacterDetailUiModel
import com.davidmerchan.presentation.home.model.CharacterUiModel

internal fun Character.toPresentation(): CharacterUiModel {
    return CharacterUiModel(
        id = id,
        name = name,
        image = image,
        isFavorite = isFavorite
    )
}

internal fun Character.toDetailPresentation(): CharacterDetailUiModel {
    return CharacterDetailUiModel(
        id = id,
        name = name,
        image = image,
        isFavorite = isFavorite,
        gender = gender,
        status = status,
        location = location.name,
        origin = origin.name,
        species = species
    )
}