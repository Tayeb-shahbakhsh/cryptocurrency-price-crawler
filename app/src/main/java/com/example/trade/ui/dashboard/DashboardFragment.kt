package com.example.trade.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.trade.R
import com.example.trade.database.room.CurrencyDao
import com.example.trade.database.room.CurrencyDatabase
import com.example.trade.database.room.CurrencyEntitiy
import com.example.trade.databinding.FragmentDashboardBinding
import com.example.trade.services.Crawler

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var exchangeBinding: FragmentDashboardBinding
    private lateinit var dao: CurrencyDao
    private var firstCV = "Bitcoin"
    private var secondCV = "Bitcoin"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        exchangeBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)


        var currencyDataList = getDataFromRoom()

        exchangeBinding.firstCurrencyDropdown.setOnItemClickListener { parent, view, position, id ->
            firstCV = parent.getItemAtPosition(position).toString()

            exchangeBinding.firstCurrencyPrice.text = currencyDataList[position].price.toString() + "$"
        }
        exchangeBinding.secondCurrencyDropdown.setOnItemClickListener { parent, view, position, id ->
            secondCV = parent.getItemAtPosition(position).toString()
        }


        //set tether price
        exchangeBinding.tetherPrice.text = persianToEnglish(currencyDataList[4].rialPrice.toString()) + " تومان"

        exchangeBinding.exchangeButton.setOnClickListener{
            //get data from room db

            //calculate the exchange
            calculateExchange(currencyDataList)
        }

        return exchangeBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateExchange(list: List<CurrencyEntitiy>) {

        val resultTextView = exchangeBinding.exchangeResulTV
        val resultContainer = exchangeBinding.resultContainer
        val firstCurrencyValue = exchangeBinding.valueCurrencyEditText.text.toString()
        val currenciesList = resources.getStringArray(R.array.currencies)
        val firstCVIndex = currenciesList.indexOf(firstCV)
        val secondCVIndex = currenciesList.indexOf(secondCV)
        var firstCurrencyPrice = list[firstCVIndex].price
        var secondCurrencyPrice = list[secondCVIndex].price

        //make prices to number
        firstCurrencyPrice = firstCurrencyPrice.replace("$","")
        firstCurrencyPrice = firstCurrencyPrice.replace(",","")
        secondCurrencyPrice = secondCurrencyPrice.replace("$","")
        secondCurrencyPrice = secondCurrencyPrice.replace(",","")

            if (firstCurrencyValue.isNotEmpty()) {
                val result =
                    (firstCurrencyPrice.toDouble() * firstCurrencyValue.toDouble()) / secondCurrencyPrice.toDouble()
                resultContainer.visibility = View.VISIBLE
                resultTextView.text = "$result ${list[secondCVIndex].name}"
            }else{
                val result =
                    firstCurrencyPrice.toDouble() / secondCurrencyPrice.toDouble()
                resultContainer.visibility = View.VISIBLE
                resultTextView.text = "$result ${list[secondCVIndex].name}"
            }


        Log.d("ccool", "calculateExchange: $firstCurrencyPrice + $secondCurrencyPrice")
    }

    private fun getDataFromRoom() : List<CurrencyEntitiy> {

        this.dao = CurrencyDatabase.buildDB(requireContext()).currencyDao()

        return dao.getAll()
    }


    override fun onResume() {
        super.onResume()

        val languages = resources.getStringArray(R.array.currencies)
        var arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, languages)

        exchangeBinding.firstCurrencyDropdown.setAdapter(arrayAdapter)
        exchangeBinding.secondCurrencyDropdown.setAdapter(arrayAdapter)
    }

    fun persianToEnglish(persianStr: String):String {
        var result = ""
        var en = '0'
        for (ch in persianStr) {
            en = ch
            when (ch) {
                '۰' -> en = '0'
                '۱' -> en = '1'
                '۲' -> en = '2'
                '۳' -> en = '3'
                '۴' -> en = '4'
                '۵' -> en = '5'
                '۶' -> en = '6'
                '۷' -> en = '7'
                '۸' -> en = '8'
                '۹' -> en = '9'
                ',' -> en = ','
            }
            result = "${result}$en"
        }
        return result
    }

}