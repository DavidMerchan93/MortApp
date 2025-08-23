package com.davidmerchan.domain.repository

interface CharactersRepository {
    suspend fun getAllCharacters(): List<Character>
    suspend fun getCharacter(id: Int): Character?
    suspend fun getFavoriteCharacters(): List<Character>
    suspend fun saveCharacterFavorite(character: Character)
    suspend fun removeCharacterFavorite(character: Character)
}