package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.entities.Character
import com.davidmerchan.domain.entities.LocationCharacter
import com.davidmerchan.domain.repository.CharactersRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetAllCharactersUseCaseTest {

    private lateinit var charactersRepository: CharactersRepository
    private lateinit var getAllCharactersUseCase: GetAllCharactersUseCase

    @Before
    fun setup() {
        charactersRepository = mockk()
        getAllCharactersUseCase = GetAllCharactersUseCase { isRefreshing ->
            getAllCharacters(isRefreshing, charactersRepository)
        }
    }

    @Test
    fun `when repository returns success then use case returns success`() = runTest {
        // Given
        val expectedCharacters = listOf(
            Character(
                id = 1,
                name = "Rick Sanchez",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                created = "2017-11-04T18:48:46.250Z",
                episode = listOf("https://rickandmortyapi.com/api/episode/1"),
                gender = "Male",
                location = LocationCharacter("Earth", "https://rickandmortyapi.com/api/location/1"),
                origin = LocationCharacter("Earth", "https://rickandmortyapi.com/api/location/1"),
                species = "Human",
                status = "Alive",
                type = "",
                url = "https://rickandmortyapi.com/api/character/1",
                isFavorite = false
            )
        )
        coEvery { charactersRepository.getAllCharacters(false) } returns Result.success(
            expectedCharacters
        )

        // When
        val result = getAllCharactersUseCase(false)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedCharacters, result.getOrNull())
    }

    @Test
    fun `when repository returns failure then use case returns failure`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { charactersRepository.getAllCharacters(false) } returns Result.failure(exception)

        // When
        val result = getAllCharactersUseCase(false)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `when isRefreshing is true then repository is called with true`() = runTest {
        // Given
        coEvery { charactersRepository.getAllCharacters(true) } returns Result.success(emptyList())

        // When
        val result = getAllCharactersUseCase(true)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList(), result.getOrNull())
    }
}