package com.davidmerchan.presentation.home.state

internal sealed interface HomeStateContract {
    data class State(
        val isLoading: Boolean = false,
        val data: List<String>? = null
    )

    sealed interface Event {}

    sealed interface Effect {}
}