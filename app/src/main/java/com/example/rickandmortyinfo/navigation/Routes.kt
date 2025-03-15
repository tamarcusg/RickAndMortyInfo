package com.example.rickandmortyinfo.navigation

import com.example.rickandmortyinfo.repository.CharacterData

internal sealed class NavRoute(val route: String) {
    data object Home: NavRoute("home")
    data object Details: NavRoute("details/")
}