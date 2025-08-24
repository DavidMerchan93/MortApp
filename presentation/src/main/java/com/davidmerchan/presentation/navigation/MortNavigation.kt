package com.davidmerchan.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.davidmerchan.presentation.detail.ui.CharacterDetailScreen
import com.davidmerchan.presentation.favorites.ui.FavoritesScreen
import com.davidmerchan.presentation.home.ui.HomeScreen

@Composable
fun MortNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Home
    ) {
        composable<NavigationRoutes.Home> {
            HomeScreen(
                onCharacterClick = { characterId ->
                    navController.navigate(
                        NavigationRoutes.CharacterDetail(characterId)
                    )
                },
                onFavoritesClick = {
                    navController.navigate(NavigationRoutes.Favorites)
                }
            )
        }

        composable<NavigationRoutes.CharacterDetail> { backStackEntry ->
            val character: NavigationRoutes.CharacterDetail = backStackEntry.toRoute()
            CharacterDetailScreen(
                characterId = character.characterId,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }

        composable<NavigationRoutes.Favorites> {
            FavoritesScreen(
                onCharacterClick = { characterId ->
                    navController.navigate(
                        NavigationRoutes.CharacterDetail(characterId)
                    )
                },
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}