package com.example.rickmorty.ui.episodes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.rickmorty.ui.state.UiState
import com.example.rickmorty.ui.theme.BadgeBackgroundRyM
import com.example.rickmorty.ui.theme.CardRyM
import com.example.rickmorty.ui.theme.DateTextRyM
import com.example.rickmorty.ui.theme.DividerRyM
import com.example.rickmorty.ui.theme.NegroRyM
import com.example.rickmorty.ui.theme.VerdeRyM
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EpisodeDetailScreen(
    id: String?,
    navController: NavController
) {
    val vm = remember { EpisodesListViewModel() }
    val state by vm.state.collectAsStateWithLifecycle()

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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(NegroRyM)
                    .padding(padding)
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
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
                        val episode = s.data.find { it.id.toString() == id }

                        if (episode == null) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No se encontró el episodio",
                                    color = Color.White
                                )
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 20.dp),
                                verticalArrangement = Arrangement.Top
                            ) {
                                Button(
                                    onClick = { navController.popBackStack() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = VerdeRyM,
                                        contentColor = Color.Black
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    contentPadding = PaddingValues(
                                        horizontal = 20.dp,
                                        vertical = 12.dp
                                    )
                                ) {
                                    Text("Volver")
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                    text = "Episode Detail",
                                    color = VerdeRyM,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Rick & Morty",
                                    color = Color.White,
                                    style = MaterialTheme.typography.headlineMedium
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(20.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = CardRyM
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(20.dp)
                                    ) {
                                        Surface(
                                            color = BadgeBackgroundRyM,
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(
                                                text = episode.episode,
                                                modifier = Modifier.padding(
                                                    horizontal = 10.dp,
                                                    vertical = 5.dp
                                                ),
                                                color = VerdeRyM,
                                                style = MaterialTheme.typography.labelMedium
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(16.dp))

                                        Text(
                                            text = episode.name,
                                            color = Color.White,
                                            style = MaterialTheme.typography.headlineSmall
                                        )

                                        Spacer(modifier = Modifier.height(20.dp))

                                        HorizontalDivider(
                                            color = DividerRyM,
                                            thickness = 1.dp
                                        )

                                        Spacer(modifier = Modifier.height(20.dp))

                                        DetailRow(
                                            label = "Air date",
                                            value = episode.air_date
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        DetailRow(
                                            label = "Episode ID",
                                            value = episode.id.toString()
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        DetailRow(
                                            label = "Season / Code",
                                            value = episode.episode
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Column {
        Text(
            text = label,
            color = VerdeRyM,
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            color = DateTextRyM,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

