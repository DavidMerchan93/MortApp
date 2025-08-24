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
) : BaseViewModel<HomeStateContract.State>(
    initialState = HomeStateContract.State()
) {

    override fun start() {
        super.start()
        fetchData()
    }

    fun handleEvent(event: HomeStateContract.Event) {
        when (event) {
            HomeStateContract.Event.RefreshData -> fetchData(true)
        }
    }

    private fun fetchData(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, isRefreshing = isRefreshing) }

            getAllCharactersUseCase(isRefreshing).onSuccess { response ->
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        isRefreshing = false,
                        data = response.map { it.toPresentation() }
                    )
                }
            }.onFailure {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        isRefreshing = false,
                        isError = true
                    )
                }
            }
        }
    }
}
