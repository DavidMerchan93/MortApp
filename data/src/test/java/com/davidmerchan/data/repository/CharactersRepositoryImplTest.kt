package com.davidmerchan.data.repository

import com.davidmerchan.database.dao.CharacterDao
import com.davidmerchan.database.entities.CharacterEntity
import com.davidmerchan.domain.repository.CharactersRepository
import com.davidmerchan.network.api.RickAndMortyApi
import com.davidmerchan.network.dto.CharacterDto
import com.davidmerchan.network.dto.CharactersResponseDto
import com.davidmerchan.network.dto.InfoDto
import com.davidmerchan.network.dto.LocationCharacterDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CharactersRepositoryImplTest {

    private lateinit var api: RickAndMortyApi
    private lateinit var database: CharacterDao
    private lateinit var repository: CharactersRepository

    @Before
    fun setup() {
        api = mockk()
        database = mockk(relaxed = true)
        repository = CharactersRepositoryImpl(api, database)
    }

    @Test
    fun `when database has characters and not refreshing then return cached characters`() =
        runTest {
            // Given
            val cachedEntities = listOf(
                CharacterEntity(
                    id = 1,
                    name = "Rick Sanchez",
                    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    created = "2017-11-04T18:48:46.250Z",
                    episode = "[\"https://rickandmortyapi.com/api/episode/1\"]",
                    gender = "Male",
                    locationName = "Earth",
                    locationUrl = "https://rickandmortyapi.com/api/location/1",
                    originName = "Earth",
                    originUrl = "https://rickandmortyapi.com/api/location/1",
                    species = "Human",
                    status = "Alive",
                    type = "",
                    url = "https://rickandmortyapi.com/api/character/1",
                    isFavorite = false
                )
            )
            coEvery { database.getAllCharacters() } returns cachedEntities

            // When
            val result = repository.getAllCharacters()

            // Then
            assertTrue(result.isSuccess)
            assertEquals(1, result.getOrNull()?.size)
            assertEquals("Rick Sanchez", result.getOrNull()?.first()?.name)
        }

    @Test
    fun `when database is empty then fetch from api and cache results`() = runTest {
        // Given
        val apiResponse = CharactersResponseDto(
            info = InfoDto(1, 1, null, null),
            results = listOf(
                CharacterDto(
                    id = 1,
                    name = "Rick Sanchez",
                    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    created = "2017-11-04T18:48:46.250Z",
                    episode = listOf("https://rickandmortyapi.com/api/episode/1"),
                    gender = "Male",
                    location = LocationCharacterDto(
                        "Earth",
                        "https://rickandmortyapi.com/api/location/1"
                    ),
                    origin = LocationCharacterDto(
                        "Earth",
                        "https://rickandmortyapi.com/api/location/1"
                    ),
                    species = "Human",
                    status = "Alive",
                    type = "",
                    url = "https://rickandmortyapi.com/api/character/1"
                )
            )
        )

        coEvery { database.getAllCharacters() } returns emptyList()
        coEvery { api.getAllCharacters() } returns apiResponse

        // When
        val result = repository.getAllCharacters(isRefreshing = false)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
        assertEquals("Rick Sanchez", result.getOrNull()?.first()?.name)
        coVerify { database.insertCharacters(any()) }
    }

    @Test
    fun `when isRefreshing is true then always fetch from api`() = runTest {
        // Given
        val apiResponse = CharactersResponseDto(
            info = InfoDto(1, 1, null, null),
            results = listOf(
                CharacterDto(
                    id = 1,
                    name = "Rick Sanchez",
                    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    created = "2017-11-04T18:48:46.250Z",
                    episode = listOf("https://rickandmortyapi.com/api/episode/1"),
                    gender = "Male",
                    location = LocationCharacterDto(
                        "Earth",
                        "https://rickandmortyapi.com/api/location/1"
                    ),
                    origin = LocationCharacterDto(
                        "Earth",
                        "https://rickandmortyapi.com/api/location/1"
                    ),
                    species = "Human",
                    status = "Alive",
                    type = "",
                    url = "https://rickandmortyapi.com/api/character/1"
                )
            )
        )

        coEvery { database.getAllCharacters() } returns listOf() // Has cached data
        coEvery { api.getAllCharacters() } returns apiResponse

        // When
        val result = repository.getAllCharacters(isRefreshing = true)

        // Then
        assertTrue(result.isSuccess)
        coVerify { api.getAllCharacters() } // API should be called even with cached data
        coVerify { database.insertCharacters(any()) }
    }
}