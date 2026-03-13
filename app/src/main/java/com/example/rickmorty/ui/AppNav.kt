package com.example.rickmorty.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickmorty.ui.episodes.EpisodeDetailScreen
import com.example.rickmorty.ui.episodes.EpisodesListScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "episodes"
    ) {
        composable("episodes") {
            EpisodesListScreen(navController)
        }

        composable("episode/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            EpisodeDetailScreen(id)
        }
    }
}