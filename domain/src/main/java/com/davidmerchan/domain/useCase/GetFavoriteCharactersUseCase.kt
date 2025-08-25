package com.davidmerchan.domain.useCase

import com.davidmerchan.core.resultOf
import com.davidmerchan.domain.entities.Character
import com.davidmerchan.domain.repository.CharactersRepository

/**
 * Use case for retrieving characters marked as favorites by the user.
 *
 * This functional interface represents the business logic for fetching favorite characters
 * from local storage only (no network calls required).
 *
 * @return Result<List<Character>> - Success with favorite characters list or failure with exception
 *
 */
fun interface GetFavoriteCharactersUseCase : suspend () -> Result<List<Character>>

/**
 * Implementation function for retrieving favorite characters from local storage.
 *
 * Since this operates entirely on local data, it should be very fast and rarely fail.
 * The operation is independent of network connectivity and API availability.
 *
 * @param charactersRepository Repository instance for data access
 * @return Result<List<Character>> Success with favorite characters or failure with exception
 *
 */
suspend fun getFavoriteCharacters(charactersRepository: CharactersRepository) =
    resultOf {
        // Get favorite characters from local storage (database only, no network)
        charactersRepository.getFavoriteCharacters()
    }
