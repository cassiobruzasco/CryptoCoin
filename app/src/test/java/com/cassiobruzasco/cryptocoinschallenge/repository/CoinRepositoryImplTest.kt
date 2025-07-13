package com.cassiobruzasco.cryptocoinschallenge.repository

import com.cassiobruzasco.cryptocoinschallenge.data.ResponseData
import com.cassiobruzasco.cryptocoinschallenge.data.model.Coin
import com.cassiobruzasco.cryptocoinschallenge.data.model.toEntity
import com.cassiobruzasco.cryptocoinschallenge.data.repository.CoinRepositoryImpl
import com.cassiobruzasco.cryptocoinschallenge.data.room.CoinDao
import com.cassiobruzasco.cryptocoinschallenge.data.service.CoinGeckoApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CoinRepositoryImplTest {

    private lateinit var coinDao: CoinDao
    private lateinit var coinGeckoApi: CoinGeckoApi
    private lateinit var repository: CoinRepositoryImpl

    private val fakeCoins = listOf(
        Coin(id = "bitcoin", name = "Bitcoin", symbol = "BTC"),
        Coin(id = "ethereum", name = "Ethereum", symbol = "ETH")
    )

    @Before
    fun setup() {
        coinDao = mock(CoinDao::class.java)
        coinGeckoApi = mock(CoinGeckoApi::class.java)
        repository = CoinRepositoryImpl(coinGeckoApi, coinDao)
    }

    @Test
    fun `returns local coins if present`() = runTest {
        `when`(coinDao.getAllCoins()).thenReturn(fakeCoins.map { it.toEntity() })

        val result = repository.getCoins()

        assertTrue(result is ResponseData.Success)
        assertEquals(2, (result as ResponseData.Success).data.size)
        verifyNoMoreInteractions(coinGeckoApi)
    }

    @Test
    fun `returns remote coins if local is empty`() = runTest {
        `when`(coinDao.getAllCoins()).thenReturn(emptyList())
        `when`(coinGeckoApi.getMarketCoins()).thenReturn(Response.success(fakeCoins))

        val result = repository.getCoins()

        assertTrue(result is ResponseData.Success)
        verify(coinGeckoApi).getMarketCoins()
        verify(coinDao).insertCoins(anyList())
    }

    @Test
    fun `returns remote coins if local fails`() = runTest {
        `when`(coinDao.getAllCoins()).thenThrow(RuntimeException("db error"))
        `when`(coinGeckoApi.getMarketCoins()).thenReturn(Response.success(fakeCoins))

        val result = repository.getCoins()

        assertTrue(result is ResponseData.Success)
        verify(coinGeckoApi).getMarketCoins()
        verify(coinDao).insertCoins(anyList())
    }

    @Test
    fun `returns remote coins directly if isRefresh = true`() = runTest {
        `when`(coinGeckoApi.getMarketCoins()).thenReturn(Response.success(fakeCoins))

        val result = repository.getCoins(isRefresh = true)

        assertTrue(result is ResponseData.Success)
        verify(coinGeckoApi).getMarketCoins()
        verify(coinDao).insertCoins(anyList())
    }

    @Test
    fun `returns error when remote fails`() = runTest {
        val errorResponse = Response.error<List<Coin>>(
            500,
            ResponseBody.create(null, "Internal Server Error")
        )
        `when`(coinDao.getAllCoins()).thenReturn(emptyList())
        `when`(coinGeckoApi.getMarketCoins()).thenReturn(errorResponse)

        val result = repository.getCoins()

        assertTrue(result is ResponseData.Error)
        verify(coinGeckoApi).getMarketCoins()
    }

    @Test
    fun `returns empty when remote returns null body`() = runTest {
        val response = Response.success<List<Coin>>(null)
        `when`(coinDao.getAllCoins()).thenReturn(emptyList())
        `when`(coinGeckoApi.getMarketCoins()).thenReturn(response)

        val result = repository.getCoins()

        assertTrue(result is ResponseData.Empty)
    }
}