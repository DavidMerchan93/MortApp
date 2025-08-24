package com.davidmerchan.presentation.detail.state

import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.presentation.detail.model.CharacterDetailUiModel

sealed interface CharacterDetailContract {
    data class State(
        val isLoading: Boolean = false,
        val data: CharacterDetailUiModel? = null,
        val isError: Boolean = false,
    )

    sealed interface Event {
        data class FetchData(val id: CharacterId) : Event

        object UpdateFavorite : Event
    }

    sealed interface Effect {
        object ShowError : Effect
    }
}
