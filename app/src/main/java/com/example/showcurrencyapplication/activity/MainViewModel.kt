package com.example.showcurrencyapplication.activity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
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
    val exchangedMoney by lazy {
        MediatorLiveData<String>().apply {
            value = "수취금액은 " + String.format(
                "%,2.2f ",
                sourceMoney.value!! * currentRate.value!!
            ) + receiveState.value!!.getCurrency() + "입니다"

        }
    }

    private fun combineLatestData(
        source: LiveData<Double>,
        current: LiveData<Double>
    ): String {
        val onlineTime = onlineTimeResult.value
        val checkins =
            checkinsResult.value // 두 값이 모두 들어올 때 까지 success를 보내지 않음
        if (onlineTime == null || checkins == null) {
            return ""
        }
        else
            return on
    }

        init {
            getCurrencyData(Korean)
        }

        fun getCurrencyData(requestCurrency: Currency) {
            requested_at.value = getRequestAtString()
            viewModelScope.launch {
                val response = withContext(viewModelScope.coroutineContext) {
                    repository.getCurrency(requestCurrency.getCurrency())
                }
                if (response.isSuccessful) {
                    val responseDto = response.body()
                    try {
                        receiveState.value = requestCurrency
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