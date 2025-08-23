package com.davidmerchan.presentation.detail.viewModel

import androidx.lifecycle.viewModelScope
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.domain.useCase.GetCharacterUseCase
import com.davidmerchan.presentation.detail.state.CharacterDetailContract
import com.davidmerchan.presentation.mapper.toDetailPresentation
import com.davidmerchan.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailUseCase: GetCharacterUseCase,
) : BaseViewModel<CharacterDetailContract.State, CharacterDetailContract.Effect>(
    CharacterDetailContract.State()
) {

    fun handleEvent(event: CharacterDetailContract.Event) {
        when (event) {
            is CharacterDetailContract.Event.FetchData -> fetchData(event.id)
        }
    }

    private fun fetchData(id: CharacterId) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getCharacterDetailUseCase(id).onSuccess {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        data = it.toDetailPresentation()
                    )
                }
            }.onFailure {
                channelEffect.send(CharacterDetailContract.Effect.ShowError)
            }
        }
    }
}