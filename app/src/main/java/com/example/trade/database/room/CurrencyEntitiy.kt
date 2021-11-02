package com.example.trade.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_entity")
data class CurrencyEntitiy(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo
    var name: String,

    @ColumnInfo
    var price: String,

    @ColumnInfo
    var rialPrice: String,

    @ColumnInfo
    var marketCap: String,

    @ColumnInfo
    var dailyVolume: String,

    @ColumnInfo
    var change: String
)


