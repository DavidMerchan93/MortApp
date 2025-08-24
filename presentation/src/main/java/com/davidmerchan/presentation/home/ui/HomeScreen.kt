package com.davidmerchan.presentation.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.presentation.R
import com.davidmerchan.presentation.components.EmptyMessageComponent
import com.davidmerchan.presentation.components.ErrorComponent
import com.davidmerchan.presentation.components.LoaderComponent
import com.davidmerchan.presentation.components.PullToRefreshBox
import com.davidmerchan.presentation.home.state.HomeStateContract
import com.davidmerchan.presentation.home.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCharacterClick: (CharacterId) -> Unit = {},
    onFavoritesClick: () -> Unit = {},
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.title_home))
                },
                actions = {
                    IconButton(onClick = onFavoritesClick) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) { padding ->
        when {
            state.isLoading -> {
                Box(
                    modifier =
                        Modifier
                            .padding(padding)
                            .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    LoaderComponent()
                }
            }

            state.data.isNotEmpty() -> {
                PullToRefreshBox(
                    isRefreshing = state.isRefreshing,
                    onRefresh = { viewModel.handleEvent(HomeStateContract.Event.RefreshData) },
                    modifier = Modifier.padding(padding),
                ) {
                    CharactersListComponent(
                        characters = state.data,
                        onCharacterClick = onCharacterClick,
                    )
                }
            }

            state.data.isEmpty() -> {
                EmptyMessageComponent()
            }

            else -> {
                ErrorComponent()
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
