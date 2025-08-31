package com.example.bankapplication.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankapplication.model.TransferType
import com.example.bankapplication.ui.components.SubmitButton
import com.example.bankapplication.ui.components.TextFieldRow
import com.example.bankapplication.viewmodel.PaymentViewModel
import androidx.compose.ui.res.stringResource
import com.example.bankapplication.R

@Composable
fun PaymentScreen(
    transferType: TransferType,
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val state by viewModel.formState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextFieldRow(
            label = stringResource(R.string.label_recipient_name),
            value = state.recipientName,
            onValueChange = { viewModel.onRecipientNameChanged(it) },
            error = if (state.recipientNameTouched && !state.isRecipientNameValid) stringResource(R.string.error_invalid_name) else null,
            maxLength = 60
        )

        TextFieldRow(
            label = stringResource(R.string.label_account_number),
            value = state.accountNumber,
            onValueChange = { viewModel.onAccountNumberChanged(it) },
            error = if (!state.isAccountNumberValid && state.accountNumberTouched) stringResource(R.string.error_invalid_account_number) else null,
            maxLength = 11
        )

        TextFieldRow(
            label = stringResource(R.string.label_amount),
            value = state.amount,
            onValueChange = { viewModel.onAmountChanged(it) },
            error = if (!state.isAmountValid && state.amountTouched) stringResource(R.string.error_invalid_amount) else null
        )

        if (transferType is TransferType.International) {
            TextFieldRow(
                label = stringResource(R.string.label_iban),
                value = state.iban,
                onValueChange = { viewModel.onIbanChanged(it) },
                error = if (!state.isIbanValid && state.ibanTouched) stringResource(R.string.error_invalid_iban) else null,
                maxLength = 34
            )
            TextFieldRow(
                label = stringResource(R.string.label_swift),
                value = state.swift,
                onValueChange = { viewModel.onSwiftChanged(it) },
                error = if (!state.isSwiftValid && state.swiftTouched) stringResource(R.string.error_invalid_swift) else null,
                maxLength = 13
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        SubmitButton(
            label = stringResource(R.string.button_send_payment),
            onClick = { /* call viewModel.sendPayment() */ },
            enabled = state.isFormValid
        )
    }
}
