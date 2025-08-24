package com.davidmerchan.data.repository

import com.davidmerchan.data.mapper.toDatabaseEntity
import com.davidmerchan.data.mapper.toDomain
import com.davidmerchan.database.dao.CharacterDao
import com.davidmerchan.domain.entities.Character
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.domain.repository.CharactersRepository
import com.davidmerchan.network.api.RickAndMortyApi
import com.davidmerchan.network.api.safeApiCall
import javax.inject.Inject

class CharactersRepositoryImpl
@Inject
constructor(
    private val api: RickAndMortyApi,
    private val database: CharacterDao,
) : CharactersRepository {
    override suspend fun getAllCharacters(isRefreshing: Boolean): Result<List<Character>> {
        return safeApiCall {
            val characters = database.getAllCharacters().map { it.toDomain() }

            if (isRefreshing || characters.isEmpty()) {
                val results = api.getAllCharacters().results
                database.insertCharacters(
                    results.map { it.toDatabaseEntity() },
                )
                results.map { it.toDomain() }
            } else {
                characters
            }
        }
    }

    override suspend fun getCharacter(id: Int): Result<Character> {
        return safeApiCall {
            val character = database.getCharacterById(id)

            if (character == null) {
                val result = api.getCharacter(id)
                database.insertCharacter(result.toDatabaseEntity())
                result.toDomain()
            } else {
                character.toDomain()
            }
        }
    }

    override suspend fun getFavoriteCharacters(): List<Character> {
        val result = database.getFavoriteCharacters()
        return result.map { it.toDomain() }
    }

    override suspend fun saveCharacterFavorite(id: CharacterId) {
        database.updateFavoriteStatus(id, true)
    }

    override suspend fun removeCharacterFavorite(id: CharacterId) {
        database.updateFavoriteStatus(id, false)
    }
}
