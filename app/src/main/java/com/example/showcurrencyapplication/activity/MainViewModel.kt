package com.example.showcurrencyapplication.activity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showcurrencyapplication.model.MainRepository
import com.example.showcurrencyapplication.model.dto.currency.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    val sendState by lazy { US }
    val receiveState by lazy { MutableStateFlow<Currency>(Korean) }
    val currentCurrency by lazy { MutableStateFlow(0.0) }
    val requested_at by lazy { MutableLiveData("") }
    val sourceMoney by lazy { MutableStateFlow(0) }

    fun getCurrencyData(requestCurrency: String) {
        val currentTimeString =
            ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM")).toString()

        requested_at.value = currentTimeString
        viewModelScope.launch {
            val response = repository.getCurrency(requestCurrency)
            if (response.isSuccessful) {
                val responseDto = response.body()
                currentCurrency.value = when (receiveState.value) {
                    is Korean ->
                        responseDto!!.quotes.USDKRW
                    is Japan ->
                        responseDto!!.quotes.USDJPY
                    is Philippine ->
                        responseDto!!.quotes.USDPHP
                    is US ->
                        1.0
                }
            }
        }
    }
}