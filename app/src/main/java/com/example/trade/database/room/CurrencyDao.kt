package com.example.trade.database.room

import androidx.room.*
import androidx.room.Dao

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currency_entity")
    fun getAll(): List<CurrencyEntitiy>

    @Insert
    fun insertAll(currency: MutableList<CurrencyEntitiy>)

    @Update
    fun update(currency: MutableList<CurrencyEntitiy>)

    @Delete
    fun delete(currency: CurrencyEntitiy)
}