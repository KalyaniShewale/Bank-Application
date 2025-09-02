package com.example.bankapplication.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.example.bankapplication.R
import com.example.bankapplication.ui.components.AppTopBar
import kotlinx.coroutines.delay

/**
 * Welcome screen simulating app initialization and security checks.
 * This screen handles the 5-second delay that mimics login authentication,
 * security verification, and app setup processes before proceeding to payments.
 * In a real application, this would be where user login and security validations occur,
 * but for this demo, we simulate that process and automatically navigate to payments.
 */

@Composable
fun WelcomeScreen(
    onContinue: () -> Unit
) {
    // Automatically trigger navigation after 5 sec
    LaunchedEffect(Unit) {
        delay(5_000) // 5 seconds
        onContinue()
    }
    Scaffold(
        topBar = {
            // ✅ Optional: Add TopBar to WelcomeScreen for consistency
            AppTopBar(
                title = stringResource(R.string.app_name),
                titleColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.welcome_message),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}
