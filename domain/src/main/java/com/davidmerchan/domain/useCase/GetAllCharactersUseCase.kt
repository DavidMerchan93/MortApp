package com.davidmerchan.domain.useCase

import com.davidmerchan.core.resultOf
import com.davidmerchan.domain.entities.Character
import com.davidmerchan.domain.repository.CharactersRepository

fun interface GetAllCharactersUseCase : suspend (Boolean) -> Result<List<Character>>

suspend fun getAllCharacters(
    isRefreshing: Boolean = false,
    charactersRepository: CharactersRepository,
): Result<List<Character>> =
    resultOf {
        val response = charactersRepository.getAllCharacters(isRefreshing)
        when {
            response.isSuccess -> response.getOrNull() ?: emptyList()
            else -> throw response.exceptionOrNull()!!
        }
    }
