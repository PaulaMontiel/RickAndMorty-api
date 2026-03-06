package com.example.rickmorty.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickmorty.ui.episodes.EpisodesListScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "episodes"
    ) {
        composable("episodes") {
            EpisodesListScreen()
        }

        composable("episode/{id}") {
            // pendiente detalle
        }
    }
}