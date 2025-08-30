package com.example.bankapplication.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.bankapplication.model.TransferType
import com.example.bankapplication.ui.screen.PaymentScreen

@Preview(showBackground = true)
@Composable
fun PreviewDomesticPaymentScreen() {
    PaymentScreen(transferType = TransferType.Domestic)
}

@Preview(showBackground = true)
@Composable
fun PreviewInternationalPaymentScreen() {
    PaymentScreen(transferType = TransferType.International)
}
