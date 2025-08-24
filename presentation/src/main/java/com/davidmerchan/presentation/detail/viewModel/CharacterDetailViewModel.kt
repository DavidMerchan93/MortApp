package com.davidmerchan.presentation.detail.viewModel

import androidx.lifecycle.viewModelScope
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.domain.useCase.GetCharacterUseCase
import com.davidmerchan.domain.useCase.RemoveCharacterFavoriteUseCase
import com.davidmerchan.domain.useCase.SaveCharacterFavoriteUseCase
import com.davidmerchan.presentation.detail.state.CharacterDetailContract
import com.davidmerchan.presentation.mapper.toDetailPresentation
import com.davidmerchan.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel
@Inject
constructor(
    private val getCharacterDetailUseCase: GetCharacterUseCase,
    private val saveCharacterFavoriteUseCase: SaveCharacterFavoriteUseCase,
    private val removeCharacterFavoriteUseCase: RemoveCharacterFavoriteUseCase,
) : BaseViewModel<CharacterDetailContract.State>(
    CharacterDetailContract.State(),
) {
    fun handleEvent(event: CharacterDetailContract.Event) {
        when (event) {
            is CharacterDetailContract.Event.FetchData -> fetchData(event.id)
            is CharacterDetailContract.Event.UpdateFavorite -> updateFavoriteState()
        }
    }

    private fun fetchData(id: CharacterId) {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }
            getCharacterDetailUseCase(id).onSuccess {
                state.update { state ->
                    state.copy(
                        isLoading = false,
                        data = it.toDetailPresentation(),
                    )
                }
            }.onFailure {
                state.update { state ->
                    state.copy(
                        isLoading = false,
                        isError = true,
                    )
                }
            }
        }
    }

    private fun updateFavoriteState() {
        val currentState = state.value.data

        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }

            if (currentState?.isFavorite == true) {
                removeFavorite()
            } else {
                saveFavorite()
            }
        }
    }

    private fun saveFavorite() {
        val currentState = state.value.data

        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }

            saveCharacterFavoriteUseCase(currentState?.id!!).onSuccess {
                state.update {
                    it.copy(
                        isLoading = false,
                        data = currentState.copy(isFavorite = true),
                    )
                }
            }.onFailure {
                state.update {
                    it.copy(
                        isLoading = false,
                        data = currentState.copy(isFavorite = currentState.isFavorite),
                    )
                }
            }
        }
    }

    private fun removeFavorite() {
        val currentState = state.value.data

        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }

            removeCharacterFavoriteUseCase(currentState?.id!!).onSuccess {
                state.update {
                    it.copy(
                        isLoading = false,
                        data = currentState.copy(isFavorite = false),
                    )
                }
            }.onFailure {
                state.update {
                    it.copy(
                        isLoading = false,
                        data = currentState.copy(isFavorite = currentState.isFavorite),
                    )
                }
            }
        }
    }
}
