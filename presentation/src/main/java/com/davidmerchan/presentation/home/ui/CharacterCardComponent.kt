package com.davidmerchan.presentation.home.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.presentation.home.model.CharacterUiModel

@Composable
fun CharacterCardComponent(
    modifier: Modifier = Modifier,
    character: CharacterUiModel,
    onPress: (CharacterId) -> Unit,
) {
    ElevatedCard(
        modifier = modifier,
        onClick = { onPress(character.id) },
    ) {
        AsyncImage(
            model =
                ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .diskCacheKey(character.id.toString())
                    .memoryCacheKey(character.id.toString())
                    .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun CharacterCardComponentPreview() {
    CharacterCardComponent(
        character =
            CharacterUiModel(
                id = 1,
                name = "",
                image = "https://rickandmortyapi.com/api/character/1",
                isFavorite = false,
            ),
        onPress = {},
    )
}
