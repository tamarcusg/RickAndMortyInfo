package com.example.rickandmortyinfo.home

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortyinfo.R
import com.example.rickandmortyinfo.navigation.NavRoute
import com.example.rickandmortyinfo.preview.RickAndMortyPreview
import com.example.rickandmortyinfo.repository.CharacterData
import com.example.rickandmortyinfo.ui.theme.RickAndMortyInfoTheme
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeScreenUIState,
    onHandleEvent: (HomeScreenEvent) -> Unit,
    navController: NavController
) {
    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        onHandleEvent(HomeScreenEvent.LoadCharacters)
    }

    LaunchedEffect(uiState) {
        snapshotFlow { lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastIndex ->
                if (lastIndex != null && lastIndex >= uiState.characters.size - 1  && uiState.nextPageUrl != null && !uiState.isLoading) {
                    onHandleEvent(HomeScreenEvent.LoadNextPage)
                }
            }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            modifier = Modifier.padding(bottom = 12.dp),
            searchString = uiState.searchString,
            enabled = !uiState.disableSearchBar,
            onValueChanged = {
                onHandleEvent(HomeScreenEvent.UpdateSearchString(it, lazyGridState))
            }
        )
        Text(
            text = stringResource(R.string.character_count_label, uiState.resultCount),
            color = MaterialTheme.colorScheme.primary
        )
        HorizontalDivider(Modifier.padding(top = 16.dp))
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                state = lazyGridState,
                columns = GridCells.Adaptive(128.dp),
            ) {
                items(uiState.characters) { character ->
                    CharacterGridItem(
                        name = character.name,
                        imageUrl = character.imageUrl,
                        onClick = {
                            val characterJson = Json.encodeToString(character)
                            navController.navigate(NavRoute.Details.route + Uri.encode(characterJson))
                        }
                    )
                }
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(36.dp))
            }
        }

    }
}

@RickAndMortyPreview
@Composable
fun Preview_HomeScreen() {
    RickAndMortyInfoTheme {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer)) {
            HomeScreen(
                uiState = HomeScreenUIState(
                    characters = listOf(
                        CharacterData(
                            id = 2,
                            name = "Morty Smith",
                            imageUrl = "",
                            species = "Human",
                            status = "Alive",
                            origin = "Earth",
                            type = "",
                            createdDate = "2017-11-04T18:50:21.651Z"
                        ),
                        CharacterData(
                            id = 2,
                            name = "Morty Smith",
                            imageUrl = "",
                            species = "Human",
                            status = "Alive",
                            origin = "Earth",
                            type = "",
                            createdDate = "2017-11-04T18:50:21.651Z"
                        )
                    )
                ),
                onHandleEvent = {},
                navController = rememberNavController()
            )
        }
    }
}