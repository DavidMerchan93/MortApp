package com.davidmerchan.domain.useCase

import com.davidmerchan.core.resultOf
import com.davidmerchan.domain.entities.Character
import com.davidmerchan.domain.repository.CharactersRepository

/**
 * Use case for retrieving all Rick and Morty characters.
 *
 * This functional interface represents the business logic for fetching all characters
 * from the repository, which handles both cached data and fresh API calls.
 *
 */
fun interface GetAllCharactersUseCase : suspend (Boolean) -> Result<List<Character>>

/**
 * Implementation function for retrieving all characters from the repository.
 */
suspend fun getAllCharacters(
    isRefreshing: Boolean = false,
    charactersRepository: CharactersRepository,
): Result<List<Character>> =
    resultOf {
        // Call repository to get characters with specified refresh behavior
        val response = charactersRepository.getAllCharacters(isRefreshing)

        when {
            // Success case: extract data or provide empty list if null
            response.isSuccess -> response.getOrNull() ?: emptyList()

            // Failure case: extract and re-throw the exception for resultOf to wrap
            else -> throw response.exceptionOrNull()!!
        }
    }
