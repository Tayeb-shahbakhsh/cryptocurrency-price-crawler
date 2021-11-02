package com.example.trade.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView

data class CurrenciesList(
    var imageView: Int,
    var name:String,
    var priceInDollar: String,
    var priceInToman: String,
    var change: String
)
