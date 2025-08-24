package com.davidmerchan.domain.useCase

import com.davidmerchan.core.resultOf
import com.davidmerchan.domain.entities.Character
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.domain.repository.CharactersRepository

fun interface GetCharacterUseCase : suspend (CharacterId) -> Result<Character>

suspend fun getCharacter(
    charactersRepository: CharactersRepository,
    id: CharacterId,
): Result<Character> =
    resultOf {
        val response = charactersRepository.getCharacter(id)
        when {
            response.isSuccess -> response.getOrNull()!!
            else -> throw response.exceptionOrNull()!!
        }
    }
