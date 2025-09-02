package com.example.bankapplication.util

import java.util.regex.Pattern


object ValidationUtils {
    // Must not be empty and at least 2 chars
    fun validateName(name: String): Boolean {
        return name.isNotBlank() && name.length >= 2
    }

    // Only digits, length between 8 and 16
    fun validateAccountNumber(account: String): Boolean {
        return account.matches(Regex("^[0-9]{8,16}$"))
    }

    // Must be a number > 0
    fun validateAmount(amount: String): Boolean {
        return amount.toDoubleOrNull()?.let { it > 0 } ?: false
    }

    // IBAN length 15–34, alphanumeric
    fun validateIban(iban: String): Boolean {
        return iban.matches(Regex("^[A-Z0-9]{15,34}$"))
    }

    // SWIFT format: 8 or 11 characters, alphanumeric
    fun validateSwift(swiftCode: String): Boolean {
        val pattern = Pattern.compile("^[A-Za-z]{4}[-]?[A-Za-z]{2}[-]?[A-Za-z0-9]{2}[-]?[A-Za-z0-9]{3}?\$")
        return pattern.matcher(swiftCode).matches() && swiftCode.length in 8..11
    }
}
