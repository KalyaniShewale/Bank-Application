package com.example.bankapplication.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapplication.model.PaymentType
import com.example.bankapplication.repo.PaymentRepositoryImpl
import com.example.bankapplication.util.ApiResult
import com.example.bankapplication.util.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


private fun Boolean.then(block: () -> Boolean) = if (this) block() else true

// Holds all form field values + validation results
data class PaymentFormState(
    val recipientName: String = "",
    val accountNumber: String = "",
    val amount: String = "",
    val iban: String = "",
    val swift: String = "",
    val isRecipientNameValid: Boolean = false,
    val isAccountNumberValid: Boolean = false,
    val isAmountValid: Boolean = false,
    val isIbanValid: Boolean = false,
    val isSwiftValid: Boolean = false,
    // track if user touched field
    val recipientNameTouched: Boolean = false,
    val accountNumberTouched: Boolean = false,
    val amountTouched: Boolean = false,
    val ibanTouched: Boolean = false,
    val swiftTouched: Boolean = false,
    val paymentType: PaymentType = PaymentType.Domestic
) {
}



@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepositoryImpl
) : ViewModel() {
    private val _formState = MutableStateFlow(PaymentFormState())
    val formState: StateFlow<PaymentFormState> = _formState.asStateFlow()

    private val _paymentResult = MutableStateFlow<ApiResult<*>?>(null)
    val paymentResult: StateFlow<ApiResult<*>?> = _paymentResult.asStateFlow()

    private val _navigationData = MutableStateFlow<NavigationData?>(null)
    val navigationData: StateFlow<NavigationData?> = _navigationData.asStateFlow()

    // --- Update each field + run validation ---
    fun onRecipientNameChanged(value: String) {
        _formState.update { currentState ->
            currentState.copy(
                recipientName = value,
                recipientNameTouched = true,
                isRecipientNameValid = ValidationUtils.validateName(value)
            )
        }
    }

    fun resetForm() {
        _formState.value = PaymentFormState()
    }

    fun onAccountNumberChanged(value: String) {
        _formState.update { currentState ->
            currentState.copy(
                accountNumber = value,
                accountNumberTouched = true,
                isAccountNumberValid = ValidationUtils.validateAccountNumber(value)
            )
        }
    }

    fun onAmountChanged(value: String) {
        _formState.update { currentState ->
            currentState.copy(
                amount = value,
                amountTouched = true,
                isAmountValid = ValidationUtils.validateAmount(value)
            )
        }
    }

    // New fields for international transfer
    fun onIbanChanged(value: String) {
        _formState.update { currentState ->
            currentState.copy(
                iban = value,
                ibanTouched = true,
                isIbanValid = ValidationUtils.validateIBAN(value)
            )
        }
    }

    fun onSwiftCodeChanged(value: String) {
        _formState.update { currentState ->
            currentState.copy(
                swift = value,
                swiftTouched = true,
                isSwiftValid = ValidationUtils.validateSWIFT(value)
            )
        }
    }

    fun setPaymentType(type: PaymentType) {
        _formState.update { it.copy(paymentType = type) }
    }

    fun sendPayment() {
        val state = _formState.value

        // Validate all fields based on payment type
        val isFormValid = when (state.paymentType) {
            PaymentType.Domestic -> {
                state.isRecipientNameValid && state.isAccountNumberValid && state.isAmountValid
            }

            PaymentType.International -> {
                state.isRecipientNameValid && state.isAccountNumberValid && state.isAmountValid &&
                        state.isIbanValid && state.isSwiftValid
            }
        }
        if (!isFormValid) {
            _paymentResult.value = ApiResult.Error("Please fix all validation errors")
            return
        }

        viewModelScope.launch {
            _paymentResult.value = ApiResult.Loading
            val amountValue = state.amount.toDoubleOrNull() ?: 0.0

            val result = paymentRepository.sendPayment(
                recipientName = state.recipientName,
                accountNumber = state.accountNumber,
                amount = amountValue,
                iban = if (state.paymentType == PaymentType.International) state.iban else null,
                swiftCode = if (state.paymentType == PaymentType.International) state.swift else null
            )

            _paymentResult.value = result

            if (result is ApiResult.Success) {
                _navigationData.value = NavigationData.Success(result.data)
            }
        }
    }

    fun resetNavigationData() {
        _navigationData.value = null
    }
}

sealed class NavigationData {
    data class Success(val paymentData: com.example.hankapplication.data.model.PaymentApiResponse) : NavigationData()
}