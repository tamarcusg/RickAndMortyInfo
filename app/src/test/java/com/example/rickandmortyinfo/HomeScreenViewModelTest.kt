package com.example.rickandmortyinfo

import com.example.rickandmortyinfo.home.HomeScreenEvent
import com.example.rickandmortyinfo.home.HomeScreenViewModel
import com.example.rickandmortyinfo.repository.ApiResult
import com.example.rickandmortyinfo.repository.CharacterRepository
import com.example.rickandmortyinfo.repository.PageData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class HomeScreenViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockCharacterRepo: CharacterRepository = mockk(relaxed = true)
    private val viewModel: HomeScreenViewModel = HomeScreenViewModel(mockCharacterRepo)
    private val pageData = PageData(
        count = 1,
        nextPage = "nextPage",
        characters = emptyList()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `verify HomeScreenEvent_LoadCharacters Success`() = runTest {
        coEvery { mockCharacterRepo.getCharacters() } returns ApiResult.Success(pageData)

        viewModel.handleEvent(HomeScreenEvent.LoadCharacters)

        assertEquals(pageData.count, viewModel.state.value.resultCount)
    }

    @Test
    fun `verify HomeScreenEvent_LoadCharacters Error`() = runTest {
        coEvery { mockCharacterRepo.getCharacters() } returns ApiResult.Error

        viewModel.handleEvent(HomeScreenEvent.LoadCharacters)

        assertNotEquals(pageData.count, viewModel.state.value.resultCount)
    }
}