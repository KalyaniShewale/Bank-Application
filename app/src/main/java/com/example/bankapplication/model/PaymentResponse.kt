package com.example.hankapplication.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data classes for payment API request/response models with serialization annotations.
 */

// API Request Models
data class DomesticPaymentRequest(
    @SerializedName("recipient_name")
    val recipientName: String,
    @SerializedName("account_number")
    val accountNumber: String,
    @SerializedName("amount")
    val amount: Double
)

data class InternationalPaymentRequest(
    @SerializedName("recipient_name")
    val recipientName: String,
    @SerializedName("account_number")
    val accountNumber: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("iban")
    val iban: String,
    @SerializedName("swift_code")
    val swiftCode: String
)

// API Response Model
data class PaymentApiResponse(
    @SerializedName("transaction_id")
    val transactionId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("recipient")
    val recipient: String
)

