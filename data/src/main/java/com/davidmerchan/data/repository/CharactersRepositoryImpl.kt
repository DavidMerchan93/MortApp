package com.davidmerchan.data.repository

import com.davidmerchan.domain.repository.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(): CharactersRepository {
    override suspend fun getAllCharacters(): List<Character> {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacter(id: Int): Character? {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteCharacters(): List<Character> {
        TODO("Not yet implemented")
    }

    override suspend fun saveCharacterFavorite(character: Character) {
        TODO("Not yet implemented")
    }

    override suspend fun removeCharacterFavorite(character: Character) {
        TODO("Not yet implemented")
    }
}