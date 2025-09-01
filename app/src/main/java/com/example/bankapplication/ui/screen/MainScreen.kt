package com.example.bankapplication.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.bankapplication.R
import com.example.bankapplication.ui.navigation.Screen

@Composable
fun MainScreen(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                listOf(
                    R.string.tab_home,
                    R.string.tab_payments,
                    R.string.tab_profile
                ).forEachIndexed { index, resId ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                            if (resId == R.string.tab_payments) {
                                navController.navigate(Screen.Payments.route)
                            }
                        },
                        label = { Text(stringResource(resId)) },
                        icon = {  }
                    )
                }
            }
        }
    ) { padding ->
        when (selectedTab) {
            0 -> HomeScreen(Modifier.padding(padding))
            1 -> {} // handled by nav to Payments
            2 -> ProfileScreen(Modifier.padding(padding))
        }
    }
}


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(stringResource(R.string.tab_home))
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(stringResource(R.string.tab_profile))
    }
}

