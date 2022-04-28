package com.example.showcurrencyapplication.common

import com.example.showcurrencyapplication.model.CurrencyAPI
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    private val BASE_URL = "https://currencylayer.com/"

    @Provides
    @Singleton
    fun getRetrofitApi(): CurrencyAPI {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(CurrencyAPI::class.java)
    }
}