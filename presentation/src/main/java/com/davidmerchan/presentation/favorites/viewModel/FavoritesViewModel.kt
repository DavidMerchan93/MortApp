package com.davidmerchan.presentation.favorites.viewModel

import androidx.lifecycle.viewModelScope
import com.davidmerchan.domain.useCase.GetFavoriteCharactersUseCase
import com.davidmerchan.presentation.favorites.state.FavoritesStateContract
import com.davidmerchan.presentation.mapper.toPresentation
import com.davidmerchan.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase
) : BaseViewModel<FavoritesStateContract.State, FavoritesStateContract.Effect>(
    initialState = FavoritesStateContract.State()
) {

    override fun start() {
        super.start()
        fetchFavoriteCharacters()
    }

    fun handleEvent(event: FavoritesStateContract.Event) {
        when (event) {
            is FavoritesStateContract.Event.FetchFavoriteCharacters -> {
                fetchFavoriteCharacters()
            }

            is FavoritesStateContract.Event.RefreshFavorites -> {
                fetchFavoriteCharacters()
            }
        }
    }

    private fun fetchFavoriteCharacters() {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(isLoading = true, isError = false)
            }

            getFavoriteCharactersUseCase().fold(
                onSuccess = { characters ->
                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            data = characters.map { it.toPresentation() },
                            isError = false
                        )
                    }
                },
                onFailure = { throwable ->
                    sendEffect(
                        FavoritesStateContract.Effect.ShowError(
                            throwable.message ?: "Unknown error"
                        )
                    )
                }
            )
        }
    }
}