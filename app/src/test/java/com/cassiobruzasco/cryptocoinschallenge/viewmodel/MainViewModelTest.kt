package com.cassiobruzasco.cryptocoinschallenge.viewmodel

import com.cassiobruzasco.cryptocoinschallenge.data.ResponseData
import com.cassiobruzasco.cryptocoinschallenge.data.model.Coin
import com.cassiobruzasco.cryptocoinschallenge.data.repository.CoinRepository
import com.cassiobruzasco.cryptocoinschallenge.ui.MainViewModel
import com.cassiobruzasco.cryptocoinschallenge.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var repository: CoinRepository
    private lateinit var viewModel: MainViewModel

    private val dispatcher = StandardTestDispatcher()
    private val testScope = TestScope(dispatcher)

    private val fakeCoins = listOf(
        Coin(id = "btc", name = "Bitcoin", symbol = "BTC"),
        Coin(id = "eth", name = "Ethereum", symbol = "ETH")
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = mock(CoinRepository::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `emits Loading and Success when coins load successfully`() = testScope.runTest {
        `when`(repository.getCoins(false)).thenReturn(ResponseData.Success(fakeCoins))

        viewModel = MainViewModel(repository, dispatcher)

        advanceUntilIdle()

        val state = viewModel.coinsUiState.value
        assertEquals(UiState.Success(fakeCoins), state)
    }

    @Test
    fun `emits Loading and Error when repository returns error`() = testScope.runTest {
        `when`(repository.getCoins(false)).thenReturn(ResponseData.Error("Network error"))

        viewModel = MainViewModel(repository, dispatcher)
        advanceUntilIdle()

        val state = viewModel.coinsUiState.value
        assertEquals(UiState.Error("Network error"), state)
    }

    @Test
    fun `emits Loading and Success with empty list when data is empty`() = testScope.runTest {
        `when`(repository.getCoins(false)).thenReturn(ResponseData.Empty)

        viewModel = MainViewModel(repository, dispatcher)
        advanceUntilIdle()

        val state = viewModel.coinsUiState.value
        assertEquals(UiState.Success(emptyList()), state)
    }
}