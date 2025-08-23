package com.davidmerchan.presentation.home.viewModel

import androidx.lifecycle.viewModelScope
import com.davidmerchan.domain.useCase.GetAllCharactersUseCase
import com.davidmerchan.presentation.home.state.HomeStateContract
import com.davidmerchan.presentation.mapper.toPresentation
import com.davidmerchan.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : BaseViewModel<HomeStateContract.State, HomeStateContract.Effect>(
    initialState = HomeStateContract.State()
) {

    override fun start() {
        super.start()
        fetchData()
    }

    fun handleEvent(event: HomeStateContract.Event) {
        when (event) {
            HomeStateContract.Event.FetchData -> fetchData()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getAllCharactersUseCase().onSuccess { response ->
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        data = response.map { it.toPresentation() }
                    )
                }
            }.onFailure {
                channelEffect.send(HomeStateContract.Effect.ShowError)
            }
        }
    }
}
