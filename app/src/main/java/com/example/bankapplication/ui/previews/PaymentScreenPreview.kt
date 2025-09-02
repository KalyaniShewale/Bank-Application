package com.example.bankapplication.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.bankapplication.model.PaymentType
import com.example.bankapplication.ui.screen.PaymentScreen

@Preview(showBackground = true)
@Composable
fun PreviewDomesticPaymentScreen() {
    PaymentScreen(transferType = PaymentType.Domestic,
        navController = rememberNavController()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewInternationalPaymentScreen() {
    PaymentScreen(transferType = PaymentType.International,
        navController = rememberNavController())

}
