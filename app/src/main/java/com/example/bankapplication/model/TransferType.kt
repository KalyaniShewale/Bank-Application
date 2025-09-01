package com.example.bankapplication.model

sealed class TransferType(val value: String) {
    object Domestic : TransferType("domestic")
    object International : TransferType("international")

    companion object {
        fun fromString(value: String?): TransferType {
            return when (value?.lowercase()) {
                Domestic.value -> Domestic
                International.value -> International
                else -> Domestic // default fallback
            }
        }
    }
}
