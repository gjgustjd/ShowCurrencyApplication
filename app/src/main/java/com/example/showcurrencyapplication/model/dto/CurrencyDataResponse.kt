package com.example.showcurrencyapplication.model.dto

data class CurrencyDataResponse(
    val privacy: String,
    val quotes: Quotes,
    val source: String,
    val success: Boolean,
    val terms: String,
    val timestamp: Int
)