package com.example.showcurrencyapplication.activity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showcurrencyapplication.model.MainRepository
import com.example.showcurrencyapplication.model.dto.currency.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    val sendState by lazy { US }
    val receiveState by lazy { MutableLiveData<Currency>(Korean) }
    val currentRate by lazy { MutableLiveData(0.0) }
    val requested_at by lazy { MutableLiveData("") }
    val sourceMoney by lazy { MutableLiveData(0) }

    init{
       getCurrencyData(Korean.getCurrency())
    }

    fun getCurrencyData(requestCurrency: String) {
        requested_at.value = getRequestAtString()
        viewModelScope.launch {
            val response = withContext(viewModelScope.coroutineContext) {
                repository.getCurrency(requestCurrency)
            }
            if (response.isSuccessful) {
                val responseDto = response.body()
                try {
                    currentRate.value = when (receiveState.value) {
                        is Korean ->
                            responseDto!!.quotes.USDKRW
                        is Japan ->
                            responseDto!!.quotes.USDJPY
                        is Philippine ->
                            responseDto!!.quotes.USDPHP
                        else -> {
                            1.0
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getRequestAtString() =
        ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString()
}