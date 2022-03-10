package com.example.trade.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CurrencyEntitiy::class], version = 2)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao() : CurrencyDao

    companion object{
        fun buildDB(context: Context) =
            Room.databaseBuilder(context.applicationContext, CurrencyDatabase::class.java, "currencydb.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

    }
}