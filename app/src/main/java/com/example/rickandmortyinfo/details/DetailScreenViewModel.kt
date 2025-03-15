package com.example.rickandmortyinfo.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyinfo.BaseViewModel
import com.example.rickandmortyinfo.ScreenEvent
import com.example.rickandmortyinfo.UIState
import com.example.rickandmortyinfo.home.HomeScreenEvent
import com.example.rickandmortyinfo.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CharacterDetailsScreenViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : BaseViewModel, ViewModel() {
    private val _state = MutableStateFlow(DetailScreenUiState())
    val state = _state.asStateFlow()

    override fun handleEvent(screenEvent: ScreenEvent) {
        when (screenEvent) {
            is DetailScreenEvent.LoadDetails -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    //when (val result = characterRepository.getCharacter())
                }
            }
        }
    }
}

internal data class DetailScreenUiState(
    val isLoading: Boolean = false
): UIState

internal sealed class DetailScreenEvent : ScreenEvent {
    data object LoadDetails : DetailScreenEvent()
}