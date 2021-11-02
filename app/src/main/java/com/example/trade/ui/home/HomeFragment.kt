package com.example.trade.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trade.R
import com.example.trade.adapter.CurrenciesList
import com.example.trade.adapter.myAdapter
import com.example.trade.database.room.CurrencyDao
import com.example.trade.database.room.CurrencyDatabase
import com.example.trade.database.room.CurrencyEntitiy
import com.example.trade.databinding.FragmentHomeBinding
import com.example.trade.services.Crawler
import com.example.trade.ui.SplachActivity

class HomeFragment() : Fragment() {


    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var dao: CurrencyDao


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        //start crawler to get currencies data
        // crawler return mutable list contains currencies data as CurrencyEntityW[EP-S
        var list = SplachActivity().getCurrencyList()

        val adapter = myAdapter(list)
        homeBinding.homerv.adapter = adapter
        homeBinding.homerv.layoutManager = LinearLayoutManager(context)

        return homeBinding.root
    }

    private fun startCrawler() : MutableList<CurrencyEntitiy>{

        val intent = Intent(requireContext(), Crawler::class.java)
        activity?.startService(intent)

        var list =  Crawler().crawl()

        dao = CurrencyDatabase.buildDB(requireContext()).currencyDao()

        if (dao.getAll().isEmpty()){
            dao.insertAll(list)
        }else {
            dao.update(list)
        }
        return list
    }

}