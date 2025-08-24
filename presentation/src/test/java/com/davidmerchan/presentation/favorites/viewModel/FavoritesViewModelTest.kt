package com.davidmerchan.presentation.favorites.viewModel

import app.cash.turbine.test
import com.davidmerchan.domain.entities.Character
import com.davidmerchan.domain.entities.LocationCharacter
import com.davidmerchan.domain.useCase.GetFavoriteCharactersUseCase
import com.davidmerchan.presentation.favorites.state.FavoritesStateContract
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    private var getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase = mockk()
    private lateinit var viewModel: FavoritesViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getFavoriteCharacters succeeds then state shows characters`() = runTest {
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
                isFavorite = true
            )
        )
        coEvery { getFavoriteCharactersUseCase() } returns Result.success(expectedCharacters)

        // When
        viewModel = FavoritesViewModel(getFavoriteCharactersUseCase)

        // Then
        viewModel.uiState.test {
            // Initial state
            val initialState = awaitItem()
            assertTrue(initialState.isLoading)
            assertFalse(initialState.isError)
            assertTrue(initialState.data.isEmpty())

            // Success state
            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertFalse(successState.isError)
            assertEquals(1, successState.data.size)
            assertEquals("Rick Sanchez", successState.data.first().name)
        }
    }

    @Test
    fun `when getFavoriteCharacters fails then state shows error`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { getFavoriteCharactersUseCase() } returns Result.failure(exception)

        // When
        viewModel = FavoritesViewModel(getFavoriteCharactersUseCase)

        // Then
        viewModel.uiState.test {
            // Initial state
            val initialState = awaitItem()
            assertTrue(initialState.isLoading)

            // Error state
            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertTrue(errorState.isError)
            assertTrue(errorState.data.isEmpty())
        }
    }

    @Test
    fun `when handleEvent RefreshFavorites then fetchFavoriteCharacters is called`() = runTest {
        // Given
        coEvery { getFavoriteCharactersUseCase() } returns Result.success(emptyList())
        viewModel = FavoritesViewModel(getFavoriteCharactersUseCase)

        // When
        viewModel.handleEvent(FavoritesStateContract.Event.RefreshFavorites)

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading) // Should complete loading
            assertTrue(state.data.isEmpty())
        }
    }
}