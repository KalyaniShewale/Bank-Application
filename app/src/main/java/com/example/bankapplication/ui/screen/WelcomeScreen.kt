package com.example.bankapplication.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.example.bankapplication.R
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    onContinue: () -> Unit
) {
    // 👇 Automatically trigger navigation after 5 sec
    LaunchedEffect(Unit) {
        delay(5_000) // 5 seconds
        onContinue()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.welcome_message),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
