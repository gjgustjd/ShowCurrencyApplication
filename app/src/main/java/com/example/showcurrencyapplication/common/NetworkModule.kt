package com.example.showcurrencyapplication.common

import com.example.showcurrencyapplication.model.CurrencyAPI
import com.example.showcurrencyapplication.model.MainRepository.Companion.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

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