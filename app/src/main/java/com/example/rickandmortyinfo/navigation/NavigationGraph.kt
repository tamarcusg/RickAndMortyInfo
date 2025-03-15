package com.example.rickandmortyinfo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rickandmortyinfo.R
import com.example.rickandmortyinfo.TopAppBarUIState
import com.example.rickandmortyinfo.details.DetailScreen
import com.example.rickandmortyinfo.home.HomeScreen
import com.example.rickandmortyinfo.home.HomeScreenViewModel
import com.example.rickandmortyinfo.repository.CharacterData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json
import java.net.URLDecoder

@Composable
internal fun NavigationGraph(
    navController: NavHostController,
    mutableTopAppBarState: MutableStateFlow<TopAppBarUIState>
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Home.route
    ) {
        composable(
            route = NavRoute.Home.route
        ) {
            mutableTopAppBarState.update { it.copy(
                title = stringResource(R.string.home_screen_title),
                showBackNav = false
            ) }

            val viewModel: HomeScreenViewModel = hiltViewModel()
            val uiState by viewModel.state.collectAsStateWithLifecycle()
            HomeScreen(
                uiState = uiState,
                onHandleEvent = viewModel::handleEvent,
                navController = navController
            )
        }

        composable(
            route = NavRoute.Details.route + "{characterData}",
            arguments = listOf(navArgument("characterData") { type = NavType.StringType})
        ) { backStackEntry ->
            val characterDataJson = backStackEntry.arguments?.getString("characterData")
            val decodedJson = URLDecoder.decode(characterDataJson, "UTF-8")
            val characterData = Json.decodeFromString<CharacterData>(decodedJson)

            mutableTopAppBarState.update { it.copy(
                title = characterData.name,
                showBackNav = true,
                onBack = { navController.popBackStack() }
            ) }

            DetailScreen(
                characterData = characterData
            )
        }
    }
}