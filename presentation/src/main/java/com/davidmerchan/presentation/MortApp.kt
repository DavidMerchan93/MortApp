package com.davidmerchan.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.davidmerchan.presentation.navigation.MortNavigation
import com.davidmerchan.presentation.theme.MortAppTheme

@Composable
fun MortApp() {
    val navController = rememberNavController()
    MortAppTheme {
        MortNavigation(navController = navController)
    }
}