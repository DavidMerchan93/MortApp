package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.repository.CharactersRepository
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(): Result<List<Character>> {
        return charactersRepository.getAllCharacters()
    }
}

/*fun interface GetAllCharactersUseCase: suspend () -> Result<Unit>

suspend fun getAllCharacters(charactersRepository: CharactersRepository): Result<Unit> = resultOf {
    charactersRepository.getAllCharacters()
}*/
