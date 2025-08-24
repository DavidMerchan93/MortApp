package com.davidmerchan.domain.useCase

import com.davidmerchan.core.resultOf
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.domain.repository.CharactersRepository

fun interface RemoveCharacterFavoriteUseCase : suspend (CharacterId) -> Result<Unit>

suspend fun removeCharacterFavorite(
    id: CharacterId,
    charactersRepository: CharactersRepository,
) = resultOf {
    charactersRepository.removeCharacterFavorite(id)
}
