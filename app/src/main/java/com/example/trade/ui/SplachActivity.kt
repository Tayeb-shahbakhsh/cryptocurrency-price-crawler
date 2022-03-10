package com.example.trade.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.trade.MainActivity
import com.example.trade.R
import com.example.trade.database.room.CurrencyDao
import com.example.trade.database.room.CurrencyDatabase
import com.example.trade.database.room.CurrencyEntitiy
import com.example.trade.services.Crawler
import kotlinx.coroutines.delay

class SplachActivity : AppCompatActivity() {

    private lateinit var dao: CurrencyDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)

        var intent = Intent(this, MainActivity::class.java)

        startCrawler()
        startActivity(intent)
    }

    private fun startCrawler() : MutableList<CurrencyEntitiy>{

        val intent = Intent(this, Crawler::class.java)
        this.startService(intent)

        var list =  Crawler().crawl()

        dao = CurrencyDatabase.buildDB(this).currencyDao()

        if (dao.getAll().isEmpty()){
            dao.insertAll(list)
        }else {
            dao.update(list)
        }
        return list
    }
}