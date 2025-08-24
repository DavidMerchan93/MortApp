package com.davidmerchan.presentation.home.state

import com.davidmerchan.presentation.home.model.CharacterUiModel

internal sealed interface HomeStateContract {
    data class State(
        val isLoading: Boolean = false,
        val data: List<CharacterUiModel> = emptyList(),
        val isError: Boolean = false,
        val isRefreshing: Boolean = false,
    )

    sealed interface Event {
        object RefreshData : Event
    }

    sealed interface Effect {
        object ShowError : Effect
    }
}
