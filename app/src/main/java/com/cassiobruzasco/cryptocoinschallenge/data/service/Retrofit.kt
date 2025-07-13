package com.cassiobruzasco.cryptocoinschallenge.data.service

import com.cassiobruzasco.cryptocoinschallenge.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    private const val GECKO_KEY_HEADER = "x-cg-demo-api-key"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader(GECKO_KEY_HEADER, BuildConfig.COIN_GECKO_API_KEY)
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.COIN_GECKO_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val geckoApi: CoinGeckoApi = retrofit.create(CoinGeckoApi::class.java)
}