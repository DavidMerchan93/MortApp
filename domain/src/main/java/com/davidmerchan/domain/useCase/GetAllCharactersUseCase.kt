package com.davidmerchan.domain.useCase

import com.davidmerchan.core.resultOf
import com.davidmerchan.domain.repository.CharactersRepository
import com.davidmerchan.domain.entities.Character

fun interface GetAllCharactersUseCase : suspend () -> Result<List<Character>>

suspend fun getAllCharacters(charactersRepository: CharactersRepository): Result<List<Character>> =
    resultOf {
        val response = charactersRepository.getAllCharacters()
        when {
            response.isSuccess -> response.getOrNull() ?: emptyList()
            else -> throw response.exceptionOrNull()!!
        }
    }
