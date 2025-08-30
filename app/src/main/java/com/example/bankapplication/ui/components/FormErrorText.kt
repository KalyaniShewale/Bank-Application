package com.example.bankapplication.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun FormErrorText(message: String) {
    Text(text = message, color = Color.Red)
}
