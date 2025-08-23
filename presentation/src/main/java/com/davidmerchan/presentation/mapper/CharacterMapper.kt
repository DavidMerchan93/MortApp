package com.davidmerchan.presentation.mapper

import com.davidmerchan.domain.entities.Character
import com.davidmerchan.presentation.home.model.CharacterUiModel

internal fun Character.toPresentation(): CharacterUiModel {
    return CharacterUiModel(
        id = id,
        name = name,
        image = image,
        isFavorite = isFavorite
    )
}