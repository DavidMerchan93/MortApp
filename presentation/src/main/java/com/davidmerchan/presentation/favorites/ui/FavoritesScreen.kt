package com.davidmerchan.presentation.favorites.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.presentation.R
import com.davidmerchan.presentation.components.EmptyMessageComponent
import com.davidmerchan.presentation.components.ErrorComponent
import com.davidmerchan.presentation.components.LoaderComponent
import com.davidmerchan.presentation.favorites.viewModel.FavoritesViewModel
import com.davidmerchan.presentation.home.ui.CharactersListComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    onCharacterClick: (CharacterId) -> Unit = {},
    onBackPressed: () -> Unit = {},
) {
    val viewModel = hiltViewModel<FavoritesViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.title_favorites))
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) { contentPadding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
        ) {
            when {
                state.isLoading -> {
                    LoaderComponent()
                }

                state.data.isNotEmpty() -> {
                    CharactersListComponent(
                        characters = state.data,
                        onCharacterClick = onCharacterClick,
                    )
                }

                state.data.isEmpty() -> {
                    EmptyMessageComponent()
                }

                state.isError -> {
                    ErrorComponent()
                }
            }
        }
    }
}
