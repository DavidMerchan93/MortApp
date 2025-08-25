package com.davidmerchan.domain.useCase

import com.davidmerchan.core.resultOf
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.domain.repository.CharactersRepository

/**
 * Use case for marking a Rick and Morty character as favorite.
 *
 * This functional interface represents the business logic for saving a character
 * to the user's favorites list in local storage.
 *
 * @param CharacterId id - The unique identifier of the character to mark as favorite
 * @return Result<Unit> - Success indication or failure with exception
 *
 */
fun interface SaveCharacterFavoriteUseCase : suspend (CharacterId) -> Result<Unit>

/**
 * Implementation function for saving a character as favorite in local storage.
 *
 * @param id The unique identifier of the character to mark as favorite (CharacterId is Int)
 * @param charactersRepository Repository instance for data access
 * @return Result<Unit> Success indication or failure with exception
 *
 */
suspend fun saveCharacterFavorite(
    id: CharacterId,
    charactersRepository: CharactersRepository,
) = resultOf {
    // Mark character as favorite in local database
    // Repository handles validation and persistence
    charactersRepository.saveCharacterFavorite(id)
}
