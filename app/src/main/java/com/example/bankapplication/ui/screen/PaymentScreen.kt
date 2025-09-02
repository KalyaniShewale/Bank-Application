package com.example.bankapplication.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankapplication.model.PaymentType
import com.example.bankapplication.ui.components.SubmitButton
import com.example.bankapplication.ui.components.TextFieldRow
import com.example.bankapplication.viewmodel.PaymentViewModel
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.bankapplication.R
import com.example.bankapplication.ui.navigation.Screen
import com.example.bankapplication.util.ApiResult
import com.example.bankapplication.viewmodel.PaymentFormState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.bankapplication.viewmodel.NavigationData


@Composable
fun PaymentScreen(
    transferType: PaymentType,
    navController: NavHostController? = null,
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val state by viewModel.formState.collectAsStateWithLifecycle()
    val paymentResult by viewModel.paymentResult.collectAsStateWithLifecycle()
    val navigationData by viewModel.navigationData.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
   // val context = LocalContext.current

    // Navigate to success screen when payment is successful
    LaunchedEffect(navigationData) {
        when (navigationData) {
            is NavigationData.Success -> {
                navController?.navigate("successScreen") {
                    popUpTo("paymentScreen/${transferType}") { inclusive = true }
                }
                // Reset navigation data after navigating
                viewModel.resetNavigationData()
            }
            else -> {
                // Other navigation cases
            }
        }
    }

    // Handle errors with Snackbar
    LaunchedEffect(paymentResult) {
        when (paymentResult) {
            is ApiResult.Error -> {
                val error = (paymentResult as ApiResult.Error).message
                snackbarHostState.showSnackbar(
                    message = error,
                    duration = SnackbarDuration.Long
                )
            }
            else -> {
                // Loading and success handled elsewhere
            }
        }
    }

    // Set payment type when screen loads
    LaunchedEffect(transferType) {
        viewModel.setPaymentType(transferType)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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

            if (transferType is PaymentType.International) {
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
                    onValueChange = { viewModel.onSwiftCodeChanged(it) },
                    error = if (!state.isSwiftValid && state.swiftTouched) stringResource(R.string.error_invalid_swift) else null,
                    maxLength = 13
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (paymentResult is ApiResult.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            } else {
                SubmitButton(
                    label = stringResource(R.string.button_send_payment),
                    onClick = { viewModel.sendPayment() },
                    enabled = isFormValid(state, transferType)
                )
            }
        }
    }
}



// Helper function to check form validity
private fun isFormValid(state: PaymentFormState, transferType: PaymentType): Boolean {
    return when (transferType) {
        PaymentType.Domestic -> {
            state.isRecipientNameValid &&
                    state.isAccountNumberValid &&
                    state.isAmountValid
        }
        PaymentType.International -> {
            state.isRecipientNameValid &&
                    state.isAccountNumberValid &&
                    state.isAmountValid &&
                    state.isIbanValid &&
                    state.isSwiftValid
        }
    }
}
