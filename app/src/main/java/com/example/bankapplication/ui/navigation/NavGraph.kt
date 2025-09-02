package com.example.bankapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bankapplication.model.PaymentType
import com.example.bankapplication.ui.screen.*
import com.example.hankapplication.screen.SuccessScreen

sealed class Screen(val route: String) {
    object Welcome : Screen(Routes.WELCOME)
    object Success : Screen(Routes.SUCCESS)
    object Main : Screen(Routes.MAIN)
    object Payments : Screen(Routes.PAYMENTS)
    object Payment : Screen(Routes.PAYMENT_WITH_ARG) {
        fun createRoute(type: String) = "${Routes.PAYMENT}/$type"
    }
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Welcome.route) {

        composable(Screen.Welcome.route) {
            WelcomeScreen(onContinue = { navController.navigate(Screen.Main.route) })
        }

        composable(Screen.Main.route) {
            MainScreen(navController)
        }

        composable(Screen.Payments.route) {
            PaymentsScreen(
                onDomesticClick = {
                    navController.navigate(Screen.Payment.createRoute(PaymentType.Domestic.value))
                },
                onInternationalClick = {
                    navController.navigate(Screen.Payment.createRoute(PaymentType.International.value))
                }
            )
        }

        composable(
            route = Screen.Payment.route,
            arguments = listOf(navArgument(Routes.ARG_TYPE) { type = NavType.StringType })
        ) { backStackEntry ->
            val typeArg = backStackEntry.arguments?.getString(Routes.ARG_TYPE)
            val transferType = PaymentType.fromString(typeArg)

            PaymentScreen(transferType = transferType, navController = navController)
        }

        composable(Screen.Success.route) {
            SuccessScreen(navController = navController)
        }
    }
}