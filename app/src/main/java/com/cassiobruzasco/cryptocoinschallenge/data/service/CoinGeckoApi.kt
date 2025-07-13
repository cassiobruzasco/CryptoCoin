package com.cassiobruzasco.cryptocoinschallenge.data.service

import com.cassiobruzasco.cryptocoinschallenge.data.model.Coin
import retrofit2.Response
import retrofit2.http.GET

private const val COIN_MARKETS_PREFIX = "coins/markets?vs_currency=usd"

interface CoinGeckoApi {
    @GET(COIN_MARKETS_PREFIX)
    suspend fun getMarketCoins(): Response<List<Coin>>
}