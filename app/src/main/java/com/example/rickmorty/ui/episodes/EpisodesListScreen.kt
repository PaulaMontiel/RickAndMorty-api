package com.example.rickmorty.ui.episodes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rickmorty.data.EpisodeDTO
import com.example.rickmorty.ui.state.UiState
import com.example.rickmorty.ui.theme.BadgeBackgroundRyM
import com.example.rickmorty.ui.theme.CardRyM
import com.example.rickmorty.ui.theme.ChipOffRyM
import com.example.rickmorty.ui.theme.DateTextRyM
import com.example.rickmorty.ui.theme.DividerRyM
import com.example.rickmorty.ui.theme.NegroRyM
import com.example.rickmorty.ui.theme.VerdeRyM

@Composable
fun EpisodesListScreen(navController: NavController) {
    val vm = remember { EpisodesListViewModel() }
    val state by vm.state.collectAsStateWithLifecycle()
    var selectedSeason by remember { mutableIntStateOf(1) }

    LaunchedEffect(Unit) {
        vm.load()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = NegroRyM
    ) {
        Scaffold(
            containerColor = NegroRyM
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(NegroRyM)
                    .padding(padding)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "Rick & Morty",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        text = "Episodes",
                        color = VerdeRyM,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                SeasonChips(
                    selectedSeason = selectedSeason,
                    onSeasonSelected = { selectedSeason = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(
                    color = DividerRyM,
                    thickness = 1.dp
                )

                Spacer(modifier = Modifier.height(16.dp))

                when (val s = state) {
                    UiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = VerdeRyM)
                        }
                    }

                    is UiState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Error: ${s.message}",
                                color = Color.White
                            )
                        }
                    }

                    is UiState.Success -> {
                        val prefix = "S${selectedSeason.toString().padStart(2, '0')}"
                        val filteredEpisodes = s.data.filter {
                            it.episode.startsWith(prefix)
                        }

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                horizontal = 20.dp,
                                vertical = 4.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(filteredEpisodes) { episode ->
                                EpisodeItemCard(
                                    episode = episode,
                                    onClick = {
                                        navController.navigate("episode/${episode.id}")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SeasonChips(
    selectedSeason: Int,
    onSeasonSelected: (Int) -> Unit
) {
    val seasons = listOf(1, 2, 3, 4, 5)

    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(seasons) { season ->
            val isSelected = season == selectedSeason

            Button(
                onClick = { onSeasonSelected(season) },
                shape = RoundedCornerShape(22.dp),
                contentPadding = PaddingValues(
                    horizontal = 24.dp,
                    vertical = 14.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) VerdeRyM else ChipOffRyM,
                    contentColor = if (isSelected) Color.Black else Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text = "Season $season",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
private fun EpisodeItemCard(
    episode: EpisodeDTO,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardRyM
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    color = BadgeBackgroundRyM,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = episode.episode,
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ),
                        color = VerdeRyM,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = episode.name,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = episode.air_date,
                color = DateTextRyM,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = ">",
                color = DateTextRyM,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}