package com.example.bankapplication.model

sealed class TransferType {
    object Domestic : TransferType()
    object International : TransferType()
}
