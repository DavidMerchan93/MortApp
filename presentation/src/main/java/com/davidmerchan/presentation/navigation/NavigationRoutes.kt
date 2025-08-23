package com.davidmerchan.presentation.navigation

import com.davidmerchan.domain.entities.CharacterId
import kotlinx.serialization.Serializable

sealed class NavigationRoutes {
    @Serializable
    data object Home : NavigationRoutes()

    @Serializable
    data class CharacterDetail(val id: CharacterId) : NavigationRoutes()

    @Serializable
    data object Favorites : NavigationRoutes()
}
