package com.example.showcurrencyapplication.model

import com.example.showcurrencyapplication.model.dto.CurrencyDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyAPI {
    @GET("api/live")
    suspend fun getCurrecies(
        @Query("access_key") access_key: String = MainRepository.api_key,
        @Query("currencies") currencies: String,
        @Query("source") sourceCurrency: String = "USD",
        @Query("format") format: String = "1"
    ): Response<CurrencyDataResponse>
}