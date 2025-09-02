package com.example.hankapplication.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bankapplication.R
import com.example.hankapplication.data.model.PaymentApiResponse

@Composable
fun SuccessScreen(
    navController: NavController,
    paymentData: PaymentApiResponse? = null
) {

    // Handle device back button press
    BackHandler {
        // NAVIGATE TO PAYMENTS SCREEN WHEN BACK BUTTON IS PRESSED
        navController.navigate("payments") {
            popUpTo(0) { inclusive = true }
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Success Icon
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = stringResource(R.string.success_icon_description),
                tint = Color.Green,
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Success Message
            Text(
                text = stringResource(R.string.success_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Do More Transactions Button
            Button(
                onClick = {
                    navController.navigate("payments") {
                        // Clear the navigation stack completely
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 32.dp)
            ) {
                Text(
                    text = stringResource(R.string.success_button_more_transactions),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}