package com.example.rickandmortyinfo.home

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyinfo.BaseViewModel
import com.example.rickandmortyinfo.ScreenEvent
import com.example.rickandmortyinfo.UIState
import com.example.rickandmortyinfo.repository.ApiResult
import com.example.rickandmortyinfo.repository.CharacterData
import com.example.rickandmortyinfo.repository.CharacterRepository
import com.example.rickandmortyinfo.repository.PageData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeScreenViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : BaseViewModel, ViewModel() {
    private val _state = MutableStateFlow(HomeScreenUIState())
    val state = _state.asStateFlow()

    override fun handleEvent(screenEvent: ScreenEvent) {
        when (screenEvent) {
            is HomeScreenEvent.LoadCharacters -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    when (val result = characterRepository.getCharacters()) {
                        is ApiResult.Success -> {
                            _state.updateStatePageData(result.data)
                        }

                        is ApiResult.Error -> {
                            _state.update { it.copy(isLoading = false) }
                        }
                    }
                }
            }

            is HomeScreenEvent.UpdateSearchString -> {
                _state.update { it.copy(searchString = screenEvent.searchString) }
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    when (val result =
                        characterRepository.getCharacters(searchString = screenEvent.searchString)) {
                        is ApiResult.Success -> {
                            screenEvent.lazyGridState.scrollToItem(0)
                            _state.updateStatePageData(result.data)
                        }

                        is ApiResult.Error -> {
                            _state.update { it.copy(isLoading = false) }
                        }
                    }
                }
            }

            is HomeScreenEvent.LoadNextPage -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true, disableSearchBar = true) }
                    when (val result =
                        characterRepository.getPage(state.value.nextPageUrl.orEmpty())) {
                        is ApiResult.Success -> {
                            _state.updateStatePageData(result.data.copy(characters = state.value.characters + result.data.characters))
                            _state.update { it.copy(disableSearchBar = false) }
                        }

                        is ApiResult.Error -> {
                            _state.update { it.copy(isLoading = false, disableSearchBar = false) }
                        }
                    }
                }

            }
        }
    }

    private fun MutableStateFlow<HomeScreenUIState>.updateStatePageData(result: PageData) {
        this.update {
            it.copy(
                characters = result.characters,
                isLoading = false,
                nextPageUrl = result.nextPage,
                resultCount = result.count
            )
        }
    }
}

internal data class HomeScreenUIState(
    val searchString: String = "",
    val characters: List<CharacterData> = emptyList(),
    val isLoading: Boolean = false,
    val nextPageUrl: String? = null,
    val resultCount: Int = 0,
    val disableSearchBar: Boolean = false
) : UIState

internal sealed class HomeScreenEvent : ScreenEvent {
    data class UpdateSearchString(val searchString: String, val lazyGridState: LazyGridState) :
        HomeScreenEvent()

    data object LoadCharacters : HomeScreenEvent()
    data object LoadNextPage : HomeScreenEvent()
}