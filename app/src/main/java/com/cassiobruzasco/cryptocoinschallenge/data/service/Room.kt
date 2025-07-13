package com.cassiobruzasco.cryptocoinschallenge.data.service

import android.content.Context
import androidx.room.Room
import com.cassiobruzasco.cryptocoinschallenge.data.room.AppDatabase
import com.cassiobruzasco.cryptocoinschallenge.data.room.CoinDao

object RoomProvider {

    private const val DATABASE_NAME = "coin_database"

    @Volatile
    private var database: AppDatabase? = null

    fun initialize(context: Context) {
        if (database == null) {
            synchronized(this) {
                if (database == null) {
                    database = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }
        }
    }

    fun getDao(): CoinDao {
        return checkNotNull(database) { "call init first" }.coinDao()
    }
}