package com.davidmerchan.presentation.home.state

internal sealed interface HomeStateContract {
    data class State(
        val isLoading: Boolean = false,
        val data: List<String> = emptyList(),
        val isError: Boolean = false
    )

    sealed interface Event {
        object FetchData : Event
    }

    sealed interface Effect {
        object ShowError: Effect
    }
}