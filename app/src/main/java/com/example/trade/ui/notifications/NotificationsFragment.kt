package com.example.trade.ui.notifications

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.trade.R
import com.example.trade.databinding.AddCurrencyDialogBinding
import com.example.trade.databinding.FragmentNotificationsBinding
import com.example.trade.databinding.ProtoCardBinding
import java.util.*
import kotlin.random.Random

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var protoBinding: FragmentNotificationsBinding

    private var watchingCurrenciesList = mutableListOf<Array<String>>(arrayOf("","",""))

    override fun onResume() {
        super.onResume()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        protoBinding = FragmentNotificationsBinding.inflate(inflater, container, false)
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        protoBinding.addCurrency.setOnClickListener{
            showAddCurrencyDialog()
        }

        return protoBinding.root

    }

    private fun showAddCurrencyDialog() {

        val dialogBinding = AddCurrencyDialogBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .show()

        val languages = resources.getStringArray(R.array.currencies)
        var arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, languages)

        dialogBinding.CurrencyDropdown.setAdapter(arrayAdapter)



        dialogBinding.submit.setOnClickListener {

            protoBinding.nothingDesc.visibility = View.GONE
            protoBinding.nothingYet.visibility = View.GONE

            var currency = dialogBinding.CurrencyDropdown.setOnItemClickListener { parent, view, position, id ->
                parent.getItemAtPosition(position).toString()
            }

            var value = dialogBinding.valueCurrencyEditText.text.toString()
            var price = dialogBinding.priceCurrencyEditText.text

            watchingCurrenciesList.add(arrayOf(value.toString(),price.toString(), currency.toString()))

            alertDialog.dismiss()
        }

    }


}
