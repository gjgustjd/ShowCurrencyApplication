package com.example.showcurrencyapplication.activity

import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter

object MainBindingAdapter {

    @RequiresApi(Build.VERSION_CODES.O)
    @BindingAdapter("bindRealtimeMoney", "bindViewModel")
    @JvmStatic
    fun bindRealtimeMoney(editText: EditText, value: Boolean, viewModel: MainViewModel) {
        if (value) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    val text = s.toString()
                    viewModel.sourceMoney.value = if (text.isBlank()) 0 else text.toInt()
                }

            })
        }
    }
}