package com.davidmerchan.data.repository

import com.davidmerchan.data.mapper.toDomain
import com.davidmerchan.domain.repository.CharactersRepository
import com.davidmerchan.network.api.RickAndMortyApi
import com.davidmerchan.network.api.safeApiCall
import javax.inject.Inject
import com.davidmerchan.domain.entities.Character

class CharactersRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi
) : CharactersRepository {
    override suspend fun getAllCharacters(): Result<List<Character>> {
        return safeApiCall {
            api.getAllCharacters()
        }.map { item ->
            item.results.map { it.toDomain() }
        }
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