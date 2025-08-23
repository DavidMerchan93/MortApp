package com.davidmerchan.presentation.home.viewModel

import com.davidmerchan.domain.useCase.GetAllCharactersUseCase
import com.davidmerchan.presentation.home.state.HomeStateContract
import com.davidmerchan.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
): BaseViewModel<HomeStateContract.State, HomeStateContract.Effect>(
    initialState = HomeStateContract.State()
) {

    fun handleEvent(event: HomeStateContract.Event) {
        when(event){
            else -> Unit
        }
    }
}
