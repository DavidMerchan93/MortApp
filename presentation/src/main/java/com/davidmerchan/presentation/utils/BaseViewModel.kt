package com.davidmerchan.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<State, Effect>(
    initialState: State
) : ViewModel() {
    protected val _state = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _state
        .stateInWhileSubscribed(scope = viewModelScope, initialValue = initialState) {
            start()
        }

    protected val channelEffect: Channel<Effect> = Channel()
    val effect = channelEffect.receiveAsFlow()

    protected open fun start() {}
}