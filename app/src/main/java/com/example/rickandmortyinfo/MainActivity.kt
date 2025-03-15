package com.example.rickandmortyinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortyinfo.navigation.NavigationGraph
import com.example.rickandmortyinfo.ui.theme.RickAndMortyInfoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            RickAndMortyInfoTheme {
                val mutableTopAppBarState = MutableStateFlow(TopAppBarUIState())
                val topAppBarUIState by mutableTopAppBarState.collectAsStateWithLifecycle()
                Scaffold(
                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(text = topAppBarUIState.title)
                            },
                            navigationIcon = {
                                if (topAppBarUIState.showBackNav) {
                                    IconButton(
                                        onClick = topAppBarUIState.onBack
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Close,
                                            contentDescription = "Close Button"
                                        )
                                    }
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        NavigationGraph(navController, mutableTopAppBarState)
                    }
                }
            }
        }
    }
}

internal data class TopAppBarUIState(
    val title: String = "",
    val showBackNav: Boolean = false,
    val onBack: () -> Unit = {}
)