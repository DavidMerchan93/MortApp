package com.davidmerchan.presentation.detail.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.presentation.components.LoaderComponent
import com.davidmerchan.presentation.detail.viewModel.CharacterDetailViewModel

@Composable
fun CharacterDetailComponent(modifier: Modifier = Modifier, character: CharacterId) {
    val viewModel = hiltViewModel<CharacterDetailViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {

            if (state.isLoading) {
                LoaderComponent()
            } else {
                Text(text = state.data?.name.orEmpty())
            }

        }
    }
}
