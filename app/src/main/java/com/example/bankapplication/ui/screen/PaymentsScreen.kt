package com.example.bankapplication.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankapplication.R
import com.example.bankapplication.ui.components.ErrorTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentsScreen(
    onDomesticClick: () -> Unit,
    onInternationalClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Tab Bar with ErrorTextField
        CenterAlignedTopAppBar(
            title = {
                ErrorTextField(
                    message = stringResource(R.string.tab_payments),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            )
        )

        // Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
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
}