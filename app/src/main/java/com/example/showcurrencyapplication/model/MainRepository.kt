package com.example.showcurrencyapplication.model

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MainRepository @Inject constructor(private val currencyAPI: CurrencyAPI) {
    companion object {
        val BASE_URL = "http://apilayer.net/"
        val api_key = "dfc757c9a7fcb84de868426541000edf"
    }

   suspend fun getCurrency(requestCurrency: String) =
        currencyAPI.getCurrecies(
            currencies = requestCurrency
        )
}