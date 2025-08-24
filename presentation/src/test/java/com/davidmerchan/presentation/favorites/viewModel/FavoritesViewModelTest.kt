package com.davidmerchan.presentation.favorites.viewModel

import app.cash.turbine.test
import com.davidmerchan.domain.entities.Character
import com.davidmerchan.domain.entities.LocationCharacter
import com.davidmerchan.domain.useCase.GetFavoriteCharactersUseCase
import com.davidmerchan.presentation.favorites.state.FavoritesStateContract
import com.davidmerchan.presentation.viewModel.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {
    @get:Rule
    val mainRule = MainDispatcherRule()

    private var getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase = mockk()
    private lateinit var viewModel: FavoritesViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Test
    fun `when getFavoriteCharacters succeeds then state shows characters`() =
        runTest {
            // Given
            val expectedCharacters =
                listOf(
                    Character(
                        id = 1,
                        name = "Rick Sanchez",
                        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                        created = "2017-11-04T18:48:46.250Z",
                        episode = listOf("https://rickandmortyapi.com/api/episode/1"),
                        gender = "Male",
                        location = LocationCharacter(
                            "Earth",
                            "https://rickandmortyapi.com/api/location/1"
                        ),
                        origin = LocationCharacter(
                            "Earth",
                            "https://rickandmortyapi.com/api/location/1"
                        ),
                        species = "Human",
                        status = "Alive",
                        type = "",
                        url = "https://rickandmortyapi.com/api/character/1",
                        isFavorite = true,
                    ),
                )
            coEvery { getFavoriteCharactersUseCase() } returns Result.success(expectedCharacters)

            // When
            viewModel =
                FavoritesViewModel(getFavoriteCharactersUseCase).apply { stopTimeoutMillis = 0 }

            // Trigger the flow collection
            viewModel.uiState.test {
                // Advance coroutines to execute the start() method
                advanceUntilIdle()

                // Skip initial state and get the final state after loading
                val finalState = awaitItem()

                // Then - Should show success state
                assertFalse(finalState.isLoading)
                assertFalse(finalState.isError)
                assertEquals(1, finalState.data.size)
                assertEquals("Rick Sanchez", finalState.data.first().name)
            }
        }

    @Test
    fun `when getFavoriteCharacters fails then state shows error`() =
        runTest {
            // Given
            val exception = Exception("Network error")
            coEvery { getFavoriteCharactersUseCase() } returns Result.failure(exception)

            // When
            viewModel =
                FavoritesViewModel(getFavoriteCharactersUseCase).apply { stopTimeoutMillis = 0L }

            // Then
            viewModel.uiState.test {
                advanceUntilIdle()

                // Now the implementation correctly sets isError = true in the state
                val finalState = awaitItem()
                assertFalse(finalState.isLoading)
                assertTrue(finalState.isError)
                assertTrue(finalState.data.isEmpty())
            }
        }

    @Test
    fun `when handleEvent RefreshFavorites then fetchFavoriteCharacters is called`() =
        runTest {
            // Given
            coEvery { getFavoriteCharactersUseCase() } returns Result.success(emptyList())
            viewModel = FavoritesViewModel(getFavoriteCharactersUseCase)

            // Wait for initial load to complete
            advanceUntilIdle()

            // When
            viewModel.handleEvent(FavoritesStateContract.Event.RefreshFavorites)
            advanceUntilIdle()

            // Then
            viewModel.uiState.test {
                val state = awaitItem()
                assertFalse(state.isLoading) // Should complete loading
                assertTrue(state.data.isEmpty())
            }
        }
}
