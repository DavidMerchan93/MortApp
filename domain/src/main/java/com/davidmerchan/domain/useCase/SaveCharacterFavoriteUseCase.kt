package com.davidmerchan.domain.useCase

import com.davidmerchan.core.resultOf
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.domain.repository.CharactersRepository

fun interface SaveCharacterFavoriteUseCase : suspend (CharacterId) -> Result<Unit>

suspend fun saveCharacterFavorite(
    id: CharacterId,
    charactersRepository: CharactersRepository,
) = resultOf {
    charactersRepository.saveCharacterFavorite(id)
}
