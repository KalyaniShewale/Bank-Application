package com.example.bankapplication.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.example.bankapplication.R
import com.example.bankapplication.util.ApiResult
import com.example.bankapplication.viewmodel.PaymentFormState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import com.example.bankapplication.ui.components.ErrorMessageRow
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bankapplication.ui.components.AppTopBar
import com.example.bankapplication.ui.components.ErrorTextField
import com.example.bankapplication.ui.navigation.Routes
import com.example.bankapplication.viewmodel.NavigationData


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    transferType: PaymentType,
    navController: NavHostController? = null,
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val state by viewModel.formState.collectAsStateWithLifecycle()
    val paymentResult by viewModel.paymentResult.collectAsStateWithLifecycle()
    val navigationData by viewModel.navigationData.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(navigationData) {
        when (navigationData) {
            is NavigationData.Success -> {
                viewModel.resetForm()
                navController?.navigate(Routes.SUCCESS) {
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

    // Handle errors with Snack bar
    LaunchedEffect(paymentResult) {
        when (paymentResult) {
            is ApiResult.Error -> {
                val error = (paymentResult as ApiResult.Error).message
                snackBarHostState.showSnackbar(
                    message = error,
                    duration = SnackbarDuration.Long
                )
            }
            else -> {
                // Loading and success handled else where
            }
        }
    }

    LaunchedEffect(transferType) {
        viewModel.setPaymentType(transferType)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
                topBar =   {
                    AppTopBar(
                        title = if (transferType == PaymentType.International) {
                            stringResource(R.string.international_transfer)
                        } else {
                            stringResource(R.string.domestic_transfer)
                        },
                        titleColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        onBackClick = { navController?.popBackStack() }
                    )
                }
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
                maxLength = 60,
                keyboardType = KeyboardType.Text,
                filter = { it.isLetter() || it.isWhitespace() || it in ".'-" }
            )

            TextFieldRow(
                label = stringResource(R.string.label_account_number),
                value = state.accountNumber,
                onValueChange = {
                    if (it.length <= 8 && it.all { char -> char.isDigit() }) {
                        viewModel.onAccountNumberChanged(it)
                    }
                },
                error = if (!state.isAccountNumberValid && state.accountNumberTouched) stringResource(R.string.error_invalid_account_number) else null,
                maxLength = 8,
                keyboardType = KeyboardType.Number
            )

            TextFieldRow(
                label = stringResource(R.string.label_amount),
                value = state.amount,
                onValueChange = {
                    if (it.matches("^\\d{0,6}(\\.\\d{0,2})?\$".toRegex())) {
                        viewModel.onAmountChanged(it)
                    }
                },
                error = if (!state.isAmountValid && state.amountTouched) {
                    if ((state.amount.toDoubleOrNull() ?: 0.0) > 99999.99) {
                        stringResource(R.string.error_invalid_amount_enter)
                    } else {
                        stringResource(R.string.error_invalid_amount)
                    }
                } else null,
                keyboardType = KeyboardType.Decimal
            )

            if (transferType is PaymentType.International) {
                TextFieldRow(
                    label = stringResource(R.string.label_iban),
                    value = state.iban,
                    onValueChange = {
                        if (it.length <= 34) {
                            viewModel.onIbanChanged(it)
                        }
                    },
                    error = if (!state.isIbanValid && state.ibanTouched) stringResource(R.string.error_invalid_iban) else null,
                    maxLength = 34,
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text
                )
                TextFieldRow(
                    label = stringResource(R.string.label_swift),
                    value = state.swift,
                    onValueChange = { viewModel.onSwiftCodeChanged(it) },
                    capitalization = KeyboardCapitalization.Characters,
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
                    onClick = { keyboardController?.hide()
                        viewModel.sendPayment() },
                    enabled = isFormValid(state, transferType)
                )

                if (paymentResult is ApiResult.Error) {
                    val errorMessage = (paymentResult as ApiResult.Error).message
                    ErrorMessageRow(message = errorMessage)
                }
            }
        }
    }
}

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

