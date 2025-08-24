package com.davidmerchan.presentation.favorites.state

import com.davidmerchan.presentation.home.model.CharacterUiModel

interface FavoritesStateContract {
    data class State(
        val isLoading: Boolean = false,
        val data: List<CharacterUiModel> = emptyList(),
        val isError: Boolean = false,
    )

    sealed interface Event {
        data object FetchFavoriteCharacters : Event

        data object RefreshFavorites : Event
    }

    sealed interface Effect {
        data class ShowError(val message: String) : Effect
    }
}
