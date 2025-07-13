package com.cassiobruzasco.cryptocoinschallenge.data.repository

import com.cassiobruzasco.cryptocoinschallenge.data.ResponseData
import com.cassiobruzasco.cryptocoinschallenge.data.mapApiResponse
import com.cassiobruzasco.cryptocoinschallenge.data.model.Coin
import com.cassiobruzasco.cryptocoinschallenge.data.model.toEntity
import com.cassiobruzasco.cryptocoinschallenge.data.room.CoinDao
import com.cassiobruzasco.cryptocoinschallenge.data.room.toDomain
import com.cassiobruzasco.cryptocoinschallenge.data.service.CoinGeckoApi

interface CoinRepository {
    suspend fun getCoins(isRefresh: Boolean = false): ResponseData<List<Coin>>
}

class CoinRepositoryImpl(
    private val api: CoinGeckoApi,
    private val dao: CoinDao
) : CoinRepository {

    private suspend fun getLocalCoins(): ResponseData<List<Coin>> {
        return try {
            val coins = dao.getAllCoins().map { it.toDomain() }
            if (coins.isNotEmpty()) {
                ResponseData.Success(coins)
            } else {
                ResponseData.Empty
            }
        } catch (e: Exception) {
            ResponseData.Error(e.message)
        }
    }

    private suspend fun getRemoteCoins(): ResponseData<List<Coin>> {
        val result = mapApiResponse { api.getMarketCoins() }
        if (result is ResponseData.Success) {
            saveCoin(result.data)
        }
        return result
    }

    override suspend fun getCoins(isRefresh: Boolean) = if (isRefresh) {
        getRemoteCoins()
    } else {
        when (val localData = getLocalCoins()) {
            ResponseData.Empty, is ResponseData.Error -> getRemoteCoins()
            is ResponseData.Success<List<Coin>> -> localData
        }
    }

    private suspend fun saveCoin(coins: List<Coin>) {
        dao.insertCoins(coins.map { it.toEntity() })
    }

}