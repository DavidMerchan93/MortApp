package com.davidmerchan.domain.useCase

import com.davidmerchan.core.resultOf
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.domain.repository.CharactersRepository

/**
 * Use case for removing a Rick and Morty character from favorites.
 *
 * This functional interface represents the business logic for removing a character
 * from the user's favorites list in local storage.
 *
 * @param CharacterId id - The unique identifier of the character to remove from favorites
 * @return Result<Unit> - Success indication or failure with exception
 *
 */
fun interface RemoveCharacterFavoriteUseCase : suspend (CharacterId) -> Result<Unit>

/**
 * Implementation function for removing a character from favorites in local storage.
 *
 * @param id The unique identifier of the character to remove from favorites (CharacterId is Int)
 * @param charactersRepository Repository instance for data access
 * @return Result<Unit> Success indication or failure with exception
 *
 */
suspend fun removeCharacterFavorite(
    id: CharacterId,
    charactersRepository: CharactersRepository,
) = resultOf {
    // Remove character from favorites in local database
    charactersRepository.removeCharacterFavorite(id)
}
