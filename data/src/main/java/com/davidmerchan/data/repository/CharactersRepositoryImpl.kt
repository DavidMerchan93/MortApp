package com.davidmerchan.data.repository

import com.davidmerchan.data.mapper.toDatabaseEntity
import com.davidmerchan.data.mapper.toDomain
import com.davidmerchan.database.dao.CharacterDao
import com.davidmerchan.domain.entities.Character
import com.davidmerchan.domain.repository.CharactersRepository
import com.davidmerchan.network.api.RickAndMortyApi
import com.davidmerchan.network.api.safeApiCall
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi,
    private val database: CharacterDao
) : CharactersRepository {
    override suspend fun getAllCharacters(hasSaveLocal: Boolean): Result<List<Character>> {
        return safeApiCall {
            val characters = database.getAllCharacters().map { it.toDomain() }

            if (hasSaveLocal || characters.isEmpty()) {
                val results = api.getAllCharacters().results
                database.insertCharacters(
                    results.map { it.toDatabaseEntity() }
                )
                results.map { it.toDomain() }
            } else {
                characters
            }
        }
    }

    override suspend fun getCharacter(id: Int): Result<Character> {
        return safeApiCall {
            api.getCharacter(id).toDomain()
        }
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