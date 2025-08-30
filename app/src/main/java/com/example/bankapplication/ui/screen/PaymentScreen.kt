package com.example.bankapplication.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bankapplication.model.TransferType
import com.example.bankapplication.ui.components.SubmitButton
import com.example.bankapplication.ui.components.TextFieldRow

@Composable
fun PaymentScreen(
    transferType: TransferType
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextFieldRow(label = "Recipient Name", value = "John Doe")
        TextFieldRow(label = "Account Number", value = "1234567890")
        TextFieldRow(label = "Amount", value = "1000")

        if (transferType is TransferType.International) {
            TextFieldRow(label = "IBAN", value = "DE89370400440532013000")
            TextFieldRow(label = "SWIFT", value = "DEUTDEFF")
        }

        Spacer(modifier = Modifier.height(24.dp))
        SubmitButton(label = "Send Payment")
    }
}
