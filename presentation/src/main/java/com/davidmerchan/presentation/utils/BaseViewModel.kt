package com.davidmerchan.presentation.utils

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<State>(
    initialState: State,
) : ViewModel() {
    @VisibleForTesting
    internal var stopTimeoutMillis: Long = 5_000

    protected val state = MutableStateFlow(initialState)
    val uiState: StateFlow<State> =
        state
            .stateInWhileSubscribed(
                scope = viewModelScope,
                initialValue = initialState,
                stopTimeoutMillis = stopTimeoutMillis,
            ) {
                start()
            }

    protected open fun start() {}
}
