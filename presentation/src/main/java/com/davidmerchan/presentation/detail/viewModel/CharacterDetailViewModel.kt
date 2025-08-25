package com.davidmerchan.presentation.detail.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.domain.useCase.GetCharacterUseCase
import com.davidmerchan.domain.useCase.RemoveCharacterFavoriteUseCase
import com.davidmerchan.domain.useCase.SaveCharacterFavoriteUseCase
import com.davidmerchan.presentation.detail.state.CharacterDetailContract
import com.davidmerchan.presentation.mapper.toDetailPresentation
import com.davidmerchan.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Character Detail screen that displays detailed character information
 * and manages favorite status.
 *
 * This ViewModel handles:
 * - Loading detailed character information by ID
 * - Managing favorite/unfavorite operations
 * - Loading states for both data fetching and favorite operations
 * - Error states for failed operations
 * - Optimistic UI updates for favorite status changes
 *
 * State Management:
 * - Uses CharacterDetailContract.State to represent UI state
 * - Supports loading, error, and detailed character data states
 * - Provides immediate feedback for favorite status changes
 *
 * Dependencies:
 * - GetCharacterUseCase: Retrieves detailed character information
 * - SaveCharacterFavoriteUseCase: Marks a character as favorite
 * - RemoveCharacterFavoriteUseCase: Removes a character from favorites
 *
 * Note: Unlike other ViewModels, this one doesn't automatically load data
 * in start() - it requires an explicit FetchData event with character ID.
 */
@HiltViewModel
class CharacterDetailViewModel
@Inject
constructor(
    /**
     * Use case for retrieving detailed character information.
     * Checks local cache first, then fetches from API if needed.
     */
    private val getCharacterDetailUseCase: GetCharacterUseCase,

    /**
     * Use case for marking a character as favorite.
     * Updates local database immediately.
     */
    private val saveCharacterFavoriteUseCase: SaveCharacterFavoriteUseCase,

    /**
     * Use case for removing a character from favorites.
     * Updates local database immediately.
     */
    private val removeCharacterFavoriteUseCase: RemoveCharacterFavoriteUseCase,
) : BaseViewModel<CharacterDetailContract.State>(
    CharacterDetailContract.State(),
) {

    /**
     * Handles UI events sent from the presentation layer.
     *
     * Supported events:
     * - FetchData(id): Loads character details for the specified ID
     * - UpdateFavorite: Toggles the favorite status of the current character
     *
     */
    fun handleEvent(event: CharacterDetailContract.Event) {
        when (event) {
            is CharacterDetailContract.Event.FetchData -> fetchData(event.id)
            is CharacterDetailContract.Event.UpdateFavorite -> updateFavoriteState()
        }
    }

    /**
     * Fetches detailed character information from the domain layer.
     *
     * This method:
     * 1. Sets loading state to show progress indicator
     * 2. Calls the use case to get character details (cache-first strategy)
     * 3. Maps domain model to detailed presentation model on success
     * 4. Sets error state on failure
     *
     */
    private fun fetchData(id: CharacterId) {
        viewModelScope.launch {
            // Set loading state
            state.update { it.copy(isLoading = true, isError = false) }

            // Execute use case to get character details
            getCharacterDetailUseCase(id).onSuccess { character ->
                // Success: Update state with character details
                state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        data = character.toDetailPresentation(), // Convert to detailed UI model
                        isError = false,
                    )
                }
            }.onFailure { exception ->
                // Failure: Set error state (character not found, network error, etc.)
                state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isError = true,
                    )
                }
                Log.e("CharacterDetailViewModel", "Failed to fetch character $id", exception)
            }
        }
    }

    /**
     * Toggles the favorite status of the currently displayed character.
     *
     * This method:
     * 1. Checks the current favorite status of the character
     * 2. Calls the appropriate save or remove favorite use case
     * 3. Sets loading state during the operation
     *
     */
    private fun updateFavoriteState() {
        val currentState = state.value.data

        // Safety check: ensure we have character data before proceeding
        if (currentState == null) {
            // Log warning or show error - no character data to update
            return
        }

        viewModelScope.launch {
            // Set loading state for the favorite operation
            state.update { it.copy(isLoading = true) }

            // Toggle based on current favorite status
            if (currentState.isFavorite) {
                removeFavorite()
            } else {
                saveFavorite()
            }
        }
    }

    /**
     * Saves the current character as a favorite.
     *
     * This method:
     * 1. Gets the current character data
     * 2. Calls the save favorite use case
     * 3. Updates the UI with new favorite status on success
     * 4. Reverts to original state on failure (preserves current status)
     */
    private fun saveFavorite() {
        val currentState = state.value.data

        viewModelScope.launch {
            // Set loading state
            state.update { it.copy(isLoading = true) }

            // Execute save favorite use case
            saveCharacterFavoriteUseCase(currentState?.id!!).onSuccess {
                // Success: Update UI to show character is now favorited
                state.update { currentUIState ->
                    currentUIState.copy(
                        isLoading = false,
                        data = currentState.copy(isFavorite = true),
                    )
                }
            }.onFailure { exception ->
                // Failure: Revert to original state, keep current favorite status
                state.update { currentUIState ->
                    currentUIState.copy(
                        isLoading = false,
                        data = currentState.copy(isFavorite = currentState.isFavorite), // Preserve original
                    )
                }
                Log.e("CharacterDetailViewModel", "Failed to save favorite", exception)
            }
        }
    }

    /**
     * Removes the current character from favorites.
     *
     * This method:
     * 1. Gets the current character data
     * 2. Calls the remove favorite use case
     * 3. Updates the UI to show character is no longer favorited on success
     * 4. Reverts to original state on failure (preserves current status)
     */
    private fun removeFavorite() {
        val currentState = state.value.data

        viewModelScope.launch {
            // Set loading state
            state.update { it.copy(isLoading = true) }

            // Execute remove favorite use case
            removeCharacterFavoriteUseCase(currentState?.id!!).onSuccess {
                // Success: Update UI to show character is no longer favorited
                state.update { currentUIState ->
                    currentUIState.copy(
                        isLoading = false,
                        data = currentState.copy(isFavorite = false),
                    )
                }
            }.onFailure { exception ->
                // Failure: Revert to original state, keep current favorite status
                state.update { currentUIState ->
                    currentUIState.copy(
                        isLoading = false,
                        data = currentState.copy(isFavorite = currentState.isFavorite), // Preserve original
                    )
                }
                Log.e("CharacterDetailViewModel", "Failed to remove favorite", exception)
            }
        }
    }
}
