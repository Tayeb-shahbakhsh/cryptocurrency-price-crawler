package com.example.trade.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.trade.R
import com.example.trade.database.room.CurrencyEntitiy
import com.example.trade.databinding.TradeCardBinding

class myAdapter (private var list: MutableList<CurrencyEntitiy>): RecyclerView.Adapter<myAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: TradeCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
       ViewHolder((TradeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.currencyChange.text = list[position].change.toString()
        holder.binding.currencyName.text = list[position].name.toString()
        holder.binding.currencyDollarPrice.text = list[position].price.toString()
        holder.binding.currencyTomanPrice.text = "${list[position].rialPrice.toString()} تومان"

        var changeText = holder.binding.currencyChange.text

        if (changeText.contains("-") ){
            holder.binding.currencyChange.setTextColor(Color.parseColor("#810303"))
        }else{
            holder.binding.currencyChange.setTextColor(Color.parseColor("#FF079114"))
        }

    }

    override fun getItemCount() = list.size
}