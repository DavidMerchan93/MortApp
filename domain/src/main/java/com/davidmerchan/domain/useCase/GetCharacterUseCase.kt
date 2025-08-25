package com.davidmerchan.domain.useCase

import com.davidmerchan.core.resultOf
import com.davidmerchan.domain.entities.Character
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.domain.repository.CharactersRepository

/**
 * Use case for retrieving a specific Rick and Morty character by ID.
 *
 * This functional interface represents the business logic for fetching a single character
 * from the repository, which checks local cache first then fetches from API if needed.
 *
 */
fun interface GetCharacterUseCase : suspend (CharacterId) -> Result<Character>

/**
 * Implementation function for retrieving a specific character from the repository.
 *
 * @param charactersRepository Repository instance for data access
 * @param id The unique identifier of the character to fetch (CharacterId is Int)
 * @return Result<Character> Success with character data or failure with exception
 *
 */
suspend fun getCharacter(
    charactersRepository: CharactersRepository,
    id: CharacterId,
): Result<Character> =
    resultOf {
        // Call repository to get character by ID (cache-first strategy)
        val response = charactersRepository.getCharacter(id)

        when {
            // Success case: extract character data
            // Using !! because we expect non-null data on success
            response.isSuccess -> response.getOrNull()!!

            // Failure case: extract and re-throw the exception for resultOf to wrap
            else -> throw response.exceptionOrNull()!!
        }
    }
