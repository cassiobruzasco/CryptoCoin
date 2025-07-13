package com.cassiobruzasco.cryptocoinschallenge.data.model

import com.cassiobruzasco.cryptocoinschallenge.data.room.CoinEntity
import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("id") val id: String,
    @SerializedName("symbol") val symbol: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("current_price") val currentPrice: Double? = null,
)

fun Coin.toEntity() = CoinEntity(
    id = id,
    symbol = symbol,
    name = name,
    image = image,
    currentPrice = currentPrice
)