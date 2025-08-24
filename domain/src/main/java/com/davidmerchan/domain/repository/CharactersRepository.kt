package com.davidmerchan.domain.repository

import com.davidmerchan.domain.entities.Character
import com.davidmerchan.domain.entities.CharacterId

interface CharactersRepository {
    suspend fun getAllCharacters(isRefreshing: Boolean = false): Result<List<Character>>
    suspend fun getCharacter(id: CharacterId): Result<Character>
    suspend fun getFavoriteCharacters(): List<Character>
    suspend fun saveCharacterFavorite(id: CharacterId)
    suspend fun removeCharacterFavorite(id: CharacterId)
}