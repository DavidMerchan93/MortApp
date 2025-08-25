package com.davidmerchan.presentation.home.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.davidmerchan.domain.useCase.GetAllCharactersUseCase
import com.davidmerchan.presentation.home.state.HomeStateContract
import com.davidmerchan.presentation.mapper.toPresentation
import com.davidmerchan.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Home screen that displays a list of Rick and Morty characters.
 *
 * This ViewModel manages the state for the main character list screen, handling:
 * - Initial data loading when the screen is opened
 * - Pull-to-refresh functionality
 * - Loading and error states
 * - Mapping domain models to presentation models
 *
 * State Management:
 * - Uses HomeStateContract.State to represent UI state
 * - Supports loading, refreshing, error, and data states
 * - Implements reactive state updates through StateFlow
 *
 * Dependencies:
 * - GetAllCharactersUseCase: Fetches character data from the domain layer
 */
@HiltViewModel
internal class HomeViewModel
@Inject
constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
) : BaseViewModel<HomeStateContract.State>(
    initialState = HomeStateContract.State(),
) {

    /**
     * Called when the UI starts observing this ViewModel's state.
     * Automatically triggers the initial data loading.
     *
     * This is invoked by the BaseViewModel when the first collector
     * starts observing the uiState StateFlow.
     */
    override fun start() {
        super.start()
        fetchData()
    }

    /**
     * Handles UI events sent from the presentation layer.
     *
     * Currently supported events:
     * - RefreshData: Triggers a manual refresh of character data
     *
     * @param event The event to process, defined in HomeStateContract.Event
     */
    fun handleEvent(event: HomeStateContract.Event) {
        when (event) {
            HomeStateContract.Event.RefreshData -> fetchData(isRefreshing = true)
        }
    }

    /**
     * Fetches character data from the domain layer and updates the UI state.
     *
     */
    private fun fetchData(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            // Set loading state based on operation type
            state.update { it.copy(isLoading = true, isRefreshing = isRefreshing) }

            // Execute the use case to get character data
            getAllCharactersUseCase(isRefreshing).onSuccess { response ->
                // Success: Update state with character data
                state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isRefreshing = false,
                        data = response.map { it.toPresentation() }, // Convert domain to UI models
                        isError = false, // Clear any previous error state
                    )
                }
            }.onFailure { exception ->
                // Failure: Update state with error flag
                state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isRefreshing = false,
                        isError = true,
                    )
                }
                Log.e("HomeViewModel", "Failed to fetch characters", exception)
            }
        }
    }
}
