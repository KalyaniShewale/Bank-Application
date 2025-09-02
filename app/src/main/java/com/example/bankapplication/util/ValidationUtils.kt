package com.example.bankapplication.util

object ValidationUtils {

    // Name Validation - returns true if valid, false if invalid
    fun validateName(name: String): Boolean {
        return when {
            name.isBlank() -> false
            name.trim().length < 2 -> false
            name.trim().length > 60 -> false
            !name.matches("^[A-Za-z\\s.'-]+\$".toRegex()) -> false
            name.trim().count { it.isLetter() } < 2 -> false
            else -> true
        }
    }

    // Account Number Validation - returns true if valid, false if invalid
    fun validateAccountNumber(accountNumber: String): Boolean {
        return when {
            accountNumber.isBlank() -> false
            accountNumber.length != 8 -> false
            !accountNumber.all { it.isDigit() } -> false
            accountNumber == "00000000" -> false
            accountNumber.toLongOrNull() == null -> false
            accountNumber.toLong() <= 0 -> false
            else -> true
        }
    }

    // Amount Validation - returns true if valid, false if invalid
    fun validateAmount(amount: String): Boolean {
        return try {
            val amountValue = amount.toDouble()
            when {
                amount.isBlank() -> false
                amountValue <= 0 -> false
                amountValue > 1000000 -> false
                !amount.matches("^[0-9]*(\\.[0-9]{1,2})?\$".toRegex()) -> false
                else -> true
            }
        } catch (e: NumberFormatException) {
            false
        }
    }

    // IBAN Validation - returns true if valid, false if invalid
    fun validateIBAN(iban: String): Boolean {
        val cleanIban = iban.replace("\\s".toRegex(), "").uppercase()
        val validCountryCodes = listOf("US", "GB", "DE", "FR", "IT", "ES", "NL", "BE", "CH", "AU", "CA", "IN")

        return when {
            iban.isBlank() -> false
            cleanIban.length < 15 -> false
            cleanIban.length > 34 -> false
            !cleanIban.matches("^[A-Z0-9]+\$".toRegex()) -> false
            !cleanIban.matches("^[A-Z]{2}[0-9]{2}[A-Z0-9]+\$".toRegex()) -> false
            cleanIban.substring(0, 2) !in validCountryCodes -> false
            else -> true
        }
    }

    // SWIFT Code Validation - returns true if valid, false if invalid
    fun validateSWIFT(swift: String): Boolean {
        val cleanSwift = swift.replace("[-\\s]".toRegex(), "").uppercase()

        return when {
            swift.isBlank() -> false
            cleanSwift.length != 8 && cleanSwift.length != 11 -> false
            !cleanSwift.matches("^[A-Z0-9]+\$".toRegex()) -> false
            !cleanSwift.matches("^[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?\$".toRegex()) -> false
            !cleanSwift.substring(0, 4).all { it.isLetter() } -> false
            !cleanSwift.substring(4, 6).all { it.isLetter() } -> false
            else -> true
        }
    }
}
