package com.example.rickandmortyinfo

interface BaseViewModel {
    fun handleEvent(screenEvent: ScreenEvent)
}

interface ScreenEvent

interface UIState