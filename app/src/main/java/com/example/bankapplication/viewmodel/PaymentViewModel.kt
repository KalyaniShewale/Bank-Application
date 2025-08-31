package com.example.bankapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bankapplication.util.ValidationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
) {
    val isFormValid: Boolean
        get() = recipientName.isNotBlank() && isRecipientNameValid &&
                accountNumber.isNotBlank() && isAccountNumberValid &&
                amount.isNotBlank() && isAmountValid &&
                (iban.isBlank() || isIbanValid) &&
                (swift.isBlank() || isSwiftValid)
}

class PaymentViewModel : ViewModel() {

    private val _formState = MutableStateFlow(PaymentFormState())
    val formState: StateFlow<PaymentFormState> = _formState

    // --- Update each field + run validation ---
    fun onRecipientNameChanged(value: String) {
        _formState.value = _formState.value.copy(
            recipientName = value,
            recipientNameTouched = true,
            isRecipientNameValid = ValidationUtils.validateName(value)
        )
    }

    fun onAccountNumberChanged(value: String) {
        _formState.value = _formState.value.copy(
            accountNumber = value,
            accountNumberTouched = true,
            isAccountNumberValid = ValidationUtils.validateAccountNumber(value)
        )
    }

    fun onAmountChanged(value: String) {
        _formState.value = _formState.value.copy(
            amount = value,
            amountTouched = true,
            isAmountValid = ValidationUtils.validateAmount(value)
        )
    }

    fun onIbanChanged(value: String) {
        _formState.value = _formState.value.copy(
            iban = value,
            ibanTouched = true,
            isIbanValid = ValidationUtils.validateIban(value)
        )
    }

    fun onSwiftChanged(value: String) {
        _formState.value = _formState.value.copy(
            swift = value,
            swiftTouched = true,
            isSwiftValid = ValidationUtils.validateSwift(value)
        )
    }
}

