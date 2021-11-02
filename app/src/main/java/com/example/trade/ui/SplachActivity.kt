package com.example.trade.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trade.MainActivity
import com.example.trade.R
import com.example.trade.database.room.CurrencyEntitiy
import com.example.trade.services.Crawler
import kotlinx.coroutines.delay

class SplachActivity : AppCompatActivity() {

    var list: MutableList<CurrencyEntitiy> = Crawler().crawl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)


        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun getCurrencyList(): MutableList<CurrencyEntitiy> {
        return list
    }
}