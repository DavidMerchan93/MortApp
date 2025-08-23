package com.davidmerchan.presentation.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.presentation.home.model.CharacterUiModel

@Composable
fun CharactersListComponent(
    modifier: Modifier = Modifier,
    characters: List<CharacterUiModel>,
    onCharacterClick: (CharacterId) -> Unit = {}
) {
    val state = rememberLazyStaggeredGridState()

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        state = state,
        verticalItemSpacing = 12.dp,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(12.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(items = characters, key = { it.id }) { character ->
            CharacterCardComponent(
                character = character,
                onPress = { onCharacterClick(character.id) }
            )
        }
    }
}

@Preview
@Composable
private fun CharactersListComponentPreview() {
    CharactersListComponent(
        characters = listOf(
            CharacterUiModel(
                id = 1,
                name = "",
                image = "https://rickandmortyapi.com/api/character/1",
                isFavorite = false,
            )
        )
    )
}
