package com.davidmerchan.domain.useCase

import com.davidmerchan.core.resultOf
import com.davidmerchan.domain.repository.CharactersRepository

fun interface GetAllCharactersUseCase: suspend () -> Result<List<Character>>

suspend fun getAllCharacters(charactersRepository: CharactersRepository): Result<List<Character>> = resultOf {
    charactersRepository.getAllCharacters()
}
