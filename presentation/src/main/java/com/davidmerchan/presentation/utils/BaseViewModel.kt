package com.davidmerchan.presentation.utils

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Abstract base class for all ViewModels in the presentation layer.
 * Provides common state management functionality using StateFlow and coroutines.
 *
 * This base class implements a reactive state management pattern where:
 * - State is held in a MutableStateFlow for internal modifications
 * - UI observes a read-only StateFlow that automatically starts data loading
 * - Memory efficiency is maintained through WhileSubscribed sharing strategy
 *
 * @param State The type of state this ViewModel manages (should be immutable data class)
 * @param initialState The initial state value when the ViewModel is created
 */
abstract class BaseViewModel<State>(
    initialState: State,
) : ViewModel() {

    /**
     * Timeout duration in milliseconds for the StateFlow sharing strategy.
     * When no collectors are active, the StateFlow will stop after this duration.
     *
     * - Default: 5000ms (5 seconds) for production
     * - Can be set to 0ms for testing to ensure immediate execution
     *
     * @VisibleForTesting allows test classes to modify this value for testing scenarios
     */
    @VisibleForTesting
    internal var stopTimeoutMillis: Long = 5_000

    /**
     * Internal mutable state holder.
     * Use state.update { } to modify the state in a thread-safe manner.
     * This should only be accessed by the ViewModel itself.
     */
    protected val state = MutableStateFlow(initialState)

    /**
     * Public read-only state exposed to the UI layer.
     *
     * Features:
     * - Uses WhileSubscribed sharing strategy for memory efficiency
     * - Automatically calls start() when UI begins observing
     * - Applies distinctUntilChanged to prevent unnecessary recompositions
     * - Maintains state during configuration changes via StateFlow
     *
     * UI should collect this using collectAsState() in Compose:
     * ```kotlin
     * val uiState by viewModel.uiState.collectAsState()
     * ```
     */
    val uiState: StateFlow<State> =
        state
            .stateInWhileSubscribed(
                scope = viewModelScope,
                initialValue = initialState,
                stopTimeoutMillis = stopTimeoutMillis,
            ) {
                start() // Called when UI starts observing
            }

    /**
     * Called automatically when the UI starts observing the uiState.
     *
     * Override this method in subclasses to implement initialization logic such as:
     * - Loading initial data from repositories
     * - Setting up data streams
     * - Triggering initial API calls
     *
     * This method is called on the main thread within viewModelScope.
     *
     * Example implementation:
     * ```kotlin
     * override fun start() {
     *     fetchInitialData()
     * }
     * ```
     */
    protected open fun start() {}
}
