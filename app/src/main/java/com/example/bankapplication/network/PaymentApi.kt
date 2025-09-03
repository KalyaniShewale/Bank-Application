package com.example.hankapplication.network

import com.example.hankapplication.data.model.DomesticPaymentRequest
import com.example.hankapplication.data.model.InternationalPaymentRequest
import com.example.hankapplication.data.model.PaymentApiResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Retrofit service interface for domestic and international payment API endpoints.
 */

interface PaymentApiService {

    @POST("payments/domestic")
    suspend fun sendDomesticPayment(
        @Header("Authorization") token: String,
        @Body request: DomesticPaymentRequest
    ): PaymentApiResponse

    @POST("payments/international")
    suspend fun sendInternationalPayment(
        @Header("Authorization") token: String,
        @Body request: InternationalPaymentRequest
    ): PaymentApiResponse

    // DEMO ONLY: Hard-coded for simplicity in this sample.
    // In a real application, store the URL securely in a build configuration
    // or environment variable to avoid exposing it in source code.
    companion object {
        const val BASE_URL = "https://api.yourbank.com/v1/"
    }
}