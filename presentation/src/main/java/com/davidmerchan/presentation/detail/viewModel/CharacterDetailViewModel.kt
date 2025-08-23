package com.davidmerchan.presentation.detail.viewModel

import androidx.lifecycle.viewModelScope
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.domain.useCase.GetCharacterUseCase
import com.davidmerchan.presentation.detail.state.CharacterDetailContract
import com.davidmerchan.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
            getCharacterDetailUseCase(id)
        }
    }
}