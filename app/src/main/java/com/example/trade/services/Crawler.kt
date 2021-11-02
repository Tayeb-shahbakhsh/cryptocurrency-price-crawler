package com.example.trade.services

import android.app.Application
import android.app.IntentService
import android.content.ContentProvider
import android.content.Context
import android.content.Intent
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import com.example.trade.MainActivity
import com.example.trade.database.room.CurrencyDao
import com.example.trade.database.room.CurrencyDatabase
import com.example.trade.database.room.CurrencyEntitiy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import java.util.logging.Handler

class Crawler {

    val url = "https://arzdigital.com/coins/"
    var currencyEntitiy = mutableListOf<CurrencyEntitiy>()



    fun crawl() : MutableList<CurrencyEntitiy> {

        runBlocking {
            val job = GlobalScope.launch(Dispatchers.IO) {

                val doc = Jsoup.connect(url).get()
                val currencyName =
                    doc.getElementsByClass("arz-coin-table__name-td arz-sort-value arz-scroll-column arz-middle-off")
                        .eachText().toTypedArray()
                val currencyPrices =
                    doc.getElementsByClass("arz-coin-table__price-td arz-sort-value").eachText()
                        .toTypedArray()
                val currencyRialPrice =
                    doc.getElementsByClass("arz-toman arz-value-unit").prev().eachText().toTypedArray()
                val currencyMarketCap =
                    doc.getElementsByClass("arz-coin-table__marketcap-td arz-sort-value").eachText()
                        .toTypedArray()
                val currencyDailyVolume =
                    doc.getElementsByClass("arz-coin-table__volume-td arz-sort-value").eachText()
                        .toTypedArray()
                val currencyChange =
                    doc.getElementsByClass("arz-coin-table__daily-swing-td arz-sort-value ").eachText()
                        .toTypedArray()


                Log.d("crawl", "crawl: " + doc.getElementsByClass("arz-coin-table__price-td arz-sort-value").text())



                for (i in 0..49){


                    currencyEntitiy.add(CurrencyEntitiy(
                        id = i+1,
                        name = currencyName[i],
                        price = currencyPrices[i],
                        rialPrice = currencyRialPrice[i],
                        marketCap = currencyMarketCap[i],
                        dailyVolume = currencyDailyVolume[i],
                        change = currencyChange[i]
                    ))
                }
            }

          job.join()
        }
        return currencyEntitiy
    }



}