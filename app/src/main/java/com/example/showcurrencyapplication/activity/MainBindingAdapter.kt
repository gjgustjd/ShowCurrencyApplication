package com.example.showcurrencyapplication.activity

import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.example.showcurrencyapplication.R
import com.example.showcurrencyapplication.model.dto.currency.Japan
import com.example.showcurrencyapplication.model.dto.currency.Korean
import com.example.showcurrencyapplication.model.dto.currency.Philippine

object MainBindingAdapter {

    @RequiresApi(Build.VERSION_CODES.O)
    @BindingAdapter("bindRealtimeMoney", "bindViewModel")
    @JvmStatic
    fun bindRealtimeMoney(editText: EditText, value: Boolean, viewModel: MainViewModel) {
        viewModel?.let {
            if (value) {
                editText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int ) {}
                    override fun onTextChanged( s: CharSequence?, start: Int, before: Int, count: Int ) {}
                    override fun afterTextChanged(s: Editable?) {
                        val text = s.toString()
                        viewModel.sourceMoney.value =
                            if (text.isBlank()) {
                                Toast.makeText(
                                    editText.context,
                                    "0 이상의 금액을 입력해주십시오.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                0
                            } else
                                text.toInt()
                    }
                })
            }
        }
    }

    @BindingAdapter("bindAmount", "bindCurrency", "bindCurrentRate")
    @JvmStatic
    fun bindAmount(textView: TextView, amount: Double, currency: String, rate: Double) {
        textView.text =
            if (amount > 10000 || amount == 0.0) {
                textView.setTextColor(Color.RED)
                "송금액이 바르지 않습니다."
            } else {
                textView.setTextColor(Color.BLACK)
                "수취금액은 " + String.format("%,2.2f ", amount * rate) + currency + "입니다"
            }

    }

    @BindingAdapter("bindViewModel")
    @JvmStatic
    @RequiresApi(Build.VERSION_CODES.O)
    fun bindSpinnerArray(spinner: Spinner, viewModel: MainViewModel) {
        viewModel?.let {
            spinner.adapter = ArrayAdapter(
                spinner.context,
                android.R.layout.simple_spinner_dropdown_item,
                spinner.resources.getStringArray(R.array.spinner_currencyItems)
            )
            spinner.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.receiveState.value =
                        when (position) {
                            0 ->
                                Korean
                            1 ->
                                Japan
                            2 ->
                                Philippine
                            else -> Korean
                        }
                    viewModel.getCurrencyData(viewModel.receiveState.value!!.getCurrency())
                }
            }
        }
    }
}