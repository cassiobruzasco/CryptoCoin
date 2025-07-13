package com.cassiobruzasco.cryptocoinschallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cassiobruzasco.cryptocoinschallenge.data.ResponseData
import com.cassiobruzasco.cryptocoinschallenge.data.model.Coin
import com.cassiobruzasco.cryptocoinschallenge.data.repository.CoinRepository
import com.cassiobruzasco.cryptocoinschallenge.data.repository.CoinRepositoryImpl
import com.cassiobruzasco.cryptocoinschallenge.data.service.RetrofitProvider
import com.cassiobruzasco.cryptocoinschallenge.data.service.RoomProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val coinRepository: CoinRepository = CoinRepositoryImpl(
        api = RetrofitProvider.geckoApi,
        dao = RoomProvider.getDao()
    ),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _coinsUiState = MutableStateFlow<UiState>(UiState.Loading(false))
    val coinsUiState = _coinsUiState.asStateFlow()

    init { getCoins() }

    fun getCoins(isRefresh: Boolean = false) {
        viewModelScope.launch(dispatcher) {
            _coinsUiState.value = UiState.Loading(isRefresh)
            _coinsUiState.value = when (val ret = coinRepository.getCoins(isRefresh)) {
                ResponseData.Empty -> UiState.Success(emptyList())
                is ResponseData.Error -> UiState.Error(ret.message.orEmpty())
                is ResponseData.Success<List<Coin>> -> UiState.Success(ret.data)
            }
        }
    }
}

sealed interface UiState {
    data class Success(val coins: List<Coin>): UiState
    data class Error(val message: String): UiState
    data class Loading(val isRefresh: Boolean): UiState
}