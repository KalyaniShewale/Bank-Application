package com.example.bankapplication.model

sealed class PaymentType(val value: String) {
    object Domestic :
        PaymentType("domestic")
    object International : PaymentType("international")

    companion object {
        fun fromString(value: String?): PaymentType {
            return when (value?.lowercase()) {
                Domestic.value -> Domestic
                International.value -> International
                else -> Domestic // default fallback
            }
        }
    }
}
