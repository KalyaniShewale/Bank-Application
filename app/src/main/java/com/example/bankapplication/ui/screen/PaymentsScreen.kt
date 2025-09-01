package com.example.bankapplication.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bankapplication.R

@Composable
fun PaymentsScreen(
    onDomesticClick: () -> Unit,
    onInternationalClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onDomesticClick, modifier = Modifier.fillMaxWidth(0.7f)) {
                Text(stringResource(R.string.domestic_transfer))
            }
            Button(onClick = onInternationalClick, modifier = Modifier.fillMaxWidth(0.7f)) {
                Text(stringResource(R.string.international_transfer))
            }
        }
    }
}
