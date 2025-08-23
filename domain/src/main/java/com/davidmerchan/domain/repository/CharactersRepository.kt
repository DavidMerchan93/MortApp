package com.davidmerchan.domain.repository

import com.davidmerchan.domain.entities.Character

interface CharactersRepository {
    suspend fun getAllCharacters(hasSaveLocal: Boolean = false): Result<List<Character>>
    suspend fun getCharacter(id: Int): Character?
    suspend fun getFavoriteCharacters(): List<Character>
    suspend fun saveCharacterFavorite(character: Character)
    suspend fun removeCharacterFavorite(character: Character)
}