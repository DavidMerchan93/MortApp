package com.davidmerchan.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.davidmerchan.presentation.detail.ui.CharacterDetailScreen
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
                }
            )
        }

        composable<NavigationRoutes.CharacterDetail> { backStackEntry ->
            val characterId = backStackEntry.destination.id
            CharacterDetailScreen(
                characterId = characterId,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }

        composable<NavigationRoutes.Favorites> {
            // TODO: Implement FavoritesScreen
        }
    }
}