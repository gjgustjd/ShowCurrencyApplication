package com.example.showcurrencyapplication.model

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MainRepository @Inject constructor(private val currencyAPI: CurrencyAPI) {
    companion object {
        val BASE_URL = "http://apilayer.net/"
        val api_key = "ee50cd7cc73c9b7a7bb3d9617cfb6b9c"
    }

   suspend fun getCurrency(requestCurrency: String) =
        currencyAPI.getCurrecies(
            currencies = requestCurrency
        )
}