package com.example.showcurrencyapplication.model.dto.currency

sealed class Currency {
    abstract fun getNation(): String
    abstract fun getCurrency(): String
}

object Korean : Currency() {
    override fun getNation() = "한국"
    override fun getCurrency() = "KRW"
}

object US : Currency() {
    override fun getNation() = "미국"
    override fun getCurrency() = "USD"
}

object Japan : Currency() {
    override fun getNation() = "일본"
    override fun getCurrency() = "JPY"
}

object Philippine : Currency() {
    override fun getNation() = "필리핀"
    override fun getCurrency() = "PHP"
}
