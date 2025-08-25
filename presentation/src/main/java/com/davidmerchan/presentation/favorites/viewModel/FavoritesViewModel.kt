package com.davidmerchan.presentation.favorites.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.davidmerchan.domain.useCase.GetFavoriteCharactersUseCase
import com.davidmerchan.presentation.favorites.state.FavoritesStateContract
import com.davidmerchan.presentation.mapper.toPresentation
import com.davidmerchan.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Favorites screen that displays user's favorite characters.
 *
 * This ViewModel manages the state for the favorites screen, handling:
 * - Loading favorite characters from local storage
 * - Refreshing the favorites list when characters are added/removed
 * - Managing loading and error states for the favorites display
 * - Operating entirely on local data (no network calls required)
 *
 * State Management:
 * - Uses FavoritesStateContract.State to represent UI state
 * - Supports loading, error, and data states
 * - Updates reactively through StateFlow when favorites change
 *
 * Dependencies:
 * - GetFavoriteCharactersUseCase: Retrieves favorite characters from local database
 *
 * Note: This ViewModel works entirely with locally stored data, providing
 * instant responses without network dependency.
 */
@HiltViewModel
class FavoritesViewModel
@Inject
constructor(
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase,
) : BaseViewModel<FavoritesStateContract.State>(
    initialState = FavoritesStateContract.State(),
) {

    /**
     * Called when the UI starts observing this ViewModel's state.
     * Automatically loads the current list of favorite characters.
     *
     * This is invoked by the BaseViewModel when the first collector
     * starts observing the uiState StateFlow.
     */
    override fun start() {
        super.start()
        fetchFavoriteCharacters()
    }

    /**
     * Handles UI events sent from the presentation layer.
     *
     * Supported events:
     * - FetchFavoriteCharacters: Loads the current favorites list
     * - RefreshFavorites: Refreshes the favorites list (same as fetch for local data)
     *
     */
    fun handleEvent(event: FavoritesStateContract.Event) {
        when (event) {
            is FavoritesStateContract.Event.FetchFavoriteCharacters -> {
                fetchFavoriteCharacters()
            }

            is FavoritesStateContract.Event.RefreshFavorites -> {
                fetchFavoriteCharacters()
            }
        }
    }

    /**
     * Fetches the current list of favorite characters from local storage.
     *
     * This method:
     * 1. Sets loading state to indicate data is being fetched
     * 2. Calls the use case to get favorite characters from local database
     * 3. Maps domain models to presentation models on success
     * 4. Updates state with error flag on failure (unlikely for local data)
     *
     */
    private fun fetchFavoriteCharacters() {
        // Set loading state and clear any previous errors
        state.update { currentState ->
            currentState.copy(isLoading = true, isError = false)
        }

        viewModelScope.launch {
            // Execute the use case to get favorite characters from local storage
            getFavoriteCharactersUseCase().onSuccess { characters ->
                // Success: Update state with favorite characters data
                state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        data = characters.map { it.toPresentation() }, // Convert domain to UI models
                        isError = false,
                    )
                }
            }.onFailure { throwable ->
                // Failure: Update state with error flag
                // This should be rare for local database operations
                state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isError = true,
                    )
                }
                Log.e("FavoritesViewModel", "Failed to fetch favorite characters", throwable)
            }
        }
    }
}
