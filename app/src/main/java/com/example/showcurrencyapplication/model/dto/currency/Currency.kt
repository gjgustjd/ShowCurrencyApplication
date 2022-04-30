package com.example.showcurrencyapplication.model.dto.currency

enum class Currency(val nation: String, val currency: String) {
    Korean("한국", "KRW"),
    US("미국", "USD"),
    Japan("일본", "JPY"),
    Philippine("필리핀", "PHP")
}