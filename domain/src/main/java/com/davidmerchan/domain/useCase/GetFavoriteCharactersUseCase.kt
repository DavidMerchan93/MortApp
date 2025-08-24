package com.davidmerchan.domain.useCase

import com.davidmerchan.core.resultOf
import com.davidmerchan.domain.entities.Character
import com.davidmerchan.domain.repository.CharactersRepository

fun interface GetFavoriteCharactersUseCase : suspend () -> Result<List<Character>>

suspend fun getFavoriteCharacters(charactersRepository: CharactersRepository) =
    resultOf {
        charactersRepository.getFavoriteCharacters()
    }
