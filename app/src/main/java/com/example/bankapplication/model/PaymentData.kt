package com.example.bankapplication.model

data class PaymentData(
    val recipientName: String = "",
    val accountNumber: String = "",
    val amount: String = "",
    val iban: String = "",
    val swift: String = ""
)
