package com.example.bankapplication.repo

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.bankapplication.util.ApiResult
import com.example.hankapplication.data.model.DomesticPaymentRequest
import com.example.hankapplication.data.model.InternationalPaymentRequest
import com.example.hankapplication.data.model.PaymentApiResponse
import com.example.hankapplication.network.PaymentApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val paymentApi: PaymentApiService
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun sendPayment(
        recipientName: String,
        accountNumber: String,
        amount: Double,
        iban: String? = null,
        swiftCode: String? = null
    ): ApiResult<PaymentApiResponse> {
        return try {

            kotlinx.coroutines.delay(1500) // 1.5 second delay

            // SIMULATE NETWORK DELAY

            // TEST DIFFERENT CONDITIONS BASED ON INPUT VALUES
            when {
                // TEST 1: FAILURE - Invalid recipient name
                recipientName.equals("fail", ignoreCase = true) -> {
                    ApiResult.Error("Recipient not found or invalid", 404)
                }

                // TEST 2: FAILURE - Insufficient funds
                amount > 10000 -> {
                    ApiResult.Error("Insufficient funds", 402)
                }

                // TEST 3: FAILURE - Invalid account number
                accountNumber.equals("00000000", ignoreCase = true) -> {
                    ApiResult.Error("Invalid account number", 400)
                }

                // TEST 4: FAILURE - Server error
                recipientName.equals("servererror", ignoreCase = true) -> {
                    ApiResult.Error("Internal server error", 500)
                }

                // TEST 5: FAILURE - Network error simulation
                recipientName.equals("networkerror", ignoreCase = true) -> {
                    throw java.io.IOException("Network connection failed")
                }

                // TEST 6: FAILURE - Invalid IBAN for international transfers
                (iban != null && iban.equals("invalid", ignoreCase = true)) -> {
                    ApiResult.Error("Invalid IBAN format", 400)
                }

                // TEST 7: FAILURE - Invalid SWIFT code
                (swiftCode != null && swiftCode.equals("invalid", ignoreCase = true)) -> {
                    ApiResult.Error("Invalid SWIFT code", 400)
                }

                // SUCCESS CASES
                else -> {
                    val isInternational = iban != null && swiftCode != null
                    val transactionType = if (isInternational) "INTERNATIONAL" else "DOMESTIC"

                    val mockResponse = PaymentApiResponse(
                        transactionId = "TX${System.currentTimeMillis()}",
                        status = "SUCCESS",
                        timestamp = java.time.LocalDateTime.now().toString(),
                        amount = amount,
                        recipient = recipientName
                    )

                    ApiResult.Success(mockResponse)
                }
            }
            /*// In a real app, you would get this from a secure storage
            val authToken = "Bearer your_auth_token_here"

            val response = if (iban != null && swiftCode != null) {
                // International Transfer
                val request = InternationalPaymentRequest(
                    recipientName = recipientName,
                    accountNumber = accountNumber,
                    amount = amount,
                    iban = iban,
                    swiftCode = swiftCode
                )
                paymentApi.sendInternationalPayment(authToken, request)
            } else {
                // Domestic Transfer
                val request = DomesticPaymentRequest(
                    recipientName = recipientName,
                    accountNumber = accountNumber,
                    amount = amount
                )
                paymentApi.sendDomesticPayment(authToken, request)
            }

            ApiResult.Success(response)*/
        } catch (e: IOException) {
            ApiResult.Error("Network error. Please check your connection.")
        } catch (e: HttpException) {
            ApiResult.Error("Server error: ${e.code()}")
        } catch (e: Exception) {
            ApiResult.Error("Unexpected error: ${e.message}")
        }
    }
}
