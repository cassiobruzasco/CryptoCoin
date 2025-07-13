package com.cassiobruzasco.cryptocoinschallenge.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cassiobruzasco.cryptocoinschallenge.data.model.Coin

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey val id: String,
    val symbol: String?,
    val name: String?,
    val image: String?,
    val currentPrice: Double?
)

fun CoinEntity.toDomain() = Coin(
    id = id,
    symbol = symbol,
    name = name,
    image = image,
    currentPrice = currentPrice
)