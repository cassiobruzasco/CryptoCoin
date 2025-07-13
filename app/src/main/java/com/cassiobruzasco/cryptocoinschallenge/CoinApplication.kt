package com.cassiobruzasco.cryptocoinschallenge

import android.app.Application
import com.cassiobruzasco.cryptocoinschallenge.data.service.RoomProvider

class CoinApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        RoomProvider.initialize(this)
    }
}