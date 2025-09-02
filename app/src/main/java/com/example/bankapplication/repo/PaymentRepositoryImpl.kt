package com.example.bankapplication.repo

import android.content.Context
import com.example.bankapplication.R
import com.example.bankapplication.util.ApiResult
import com.example.hankapplication.data.model.PaymentApiResponse
import com.example.hankapplication.network.PaymentApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Repository implementation handling payment operations with simulated API responses for testing.
 */
class PaymentRepositoryImpl @Inject constructor(
    private val paymentApi: PaymentApiService,
    private val context: Context
) {
    suspend fun sendPayment(
        recipientName: String,
        accountNumber: String,
        amount: Double,
        iban: String? = null,
        swiftCode: String? = null,
        isInternational: Boolean = false
    ): ApiResult<PaymentApiResponse> {
        return try {
            kotlinx.coroutines.delay(1500)
            // this is for testing purposes only
            when {
                recipientName.equals("fail", ignoreCase = true) -> {
                    ApiResult.Error(context.getString(R.string.error_recipient_not_found), 404)
                }
                amount < 100 -> {
                    ApiResult.Error(context.getString(R.string.error_insufficient_funds), 402)
                }
                accountNumber.equals("11111111", ignoreCase = true) -> {
                    ApiResult.Error(context.getString(R.string.error_invalid_account), 400)
                }
                recipientName.equals("servererror", ignoreCase = true) -> {
                    ApiResult.Error(context.getString(R.string.error_server), 500)
                }
                recipientName.equals("NetworkError", ignoreCase = true) -> {
                    throw java.io.IOException("Network connection failed")
                }
                (isInternational && iban?.equals("invalid", ignoreCase = true) == true) -> {
                    ApiResult.Error(context.getString(R.string.error_invalid_iban), 400)
                }
                (isInternational && swiftCode?.equals("invalid", ignoreCase = true) == true) -> {
                    ApiResult.Error(context.getString(R.string.error_invalid_swift), 400)
                }
                (isInternational && recipientName.equals("failed", ignoreCase = true)) -> {
                    ApiResult.Error(context.getString(R.string.error_international_failed), 402)
                }
                else -> {
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

            // Replace with actual API call when available
           /* // Create appropriate request based on transfer type
            val response = if (isInternational) {
                val internationalRequest = InternationalPaymentRequest(
                    recipientName = recipientName,
                    accountNumber = accountNumber,
                    amount = amount,
                    iban = iban ?: "",
                    swiftCode = swiftCode ?: ""
                )
                paymentApi.sendInternationalPayment("Bearer your_auth_token_here", internationalRequest)
            } else {
                val domesticRequest = DomesticPaymentRequest(
                    recipientName = recipientName,
                    accountNumber = accountNumber,
                    amount = amount
                )
                paymentApi.sendDomesticPayment("Bearer your_auth_token_here", domesticRequest)
            }
            ApiResult.Success(response)*/

        } catch (e: IOException) {
            ApiResult.Error(context.getString(R.string.error_network))
        } catch (e: HttpException) {
            ApiResult.Error(context.getString(R.string.error_server_generic, e.code()))
        } catch (e: Exception) {
            ApiResult.Error(context.getString(R.string.error_unexpected, e.message ?: "Unknown"))
        }
    }
}
