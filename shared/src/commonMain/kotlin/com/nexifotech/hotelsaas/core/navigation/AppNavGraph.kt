package com.nexifotech.hotelsaas.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nexifotech.hotelsaas.core.ui.adaptive.AdaptiveNavigationLayout
import com.nexifotech.hotelsaas.feature.AuthScreen
import org.koin.compose.koinInject

/**
 * Root navigation graph for the entire application.
 *
 * Graph structure:
 * ```
 * NavHost (startDestination = Auth)
 * ├── Auth  →  AuthScreen  (auto-redirects to Main when real auth is not yet implemented)
 * └── Main  →  AdaptiveNavigationLayout
 *                └── Inner NavHost (startDestination = Dashboard)
 *                    ├── Dashboard, FrontOffice, Reservations, RoomManagement
 *                    ├── GuestManagement, Billing, Housekeeping, Restaurant
 *                    └── Reports, Settings, Payroll, Expenses, UserManagement, BackupAndSecurity
 * ```
 *
 * All [AppNavigator] events are collected here via [LaunchedEffect] and forwarded
 * to the [NavController], keeping ViewModels and use-cases decoupled from navigation.
 */
@Composable
fun AppNavGraph(
    navigator: AppNavigator = koinInject(),
) {
    val navController = rememberNavController()

    // Collect imperative navigation commands from the AppNavigator singleton
    LaunchedEffect(navigator) {
        navigator.navigationEvents.collect { event ->
            when (event) {
                is AppNavigator.NavigationEvent.Navigate -> {
                    navController.navigate(event.route)
                }

                is AppNavigator.NavigationEvent.NavigateUp -> {
                    navController.navigateUp()
                }

                is AppNavigator.NavigationEvent.NavigateAndPopUpTo -> {
                    navController.navigate(event.route) {
                        popUpTo(event.popUpTo) {
                            inclusive = event.inclusive
                        }
                    }
                }
            }
        }
    }

    NavHost(
        navController    = navController,
        startDestination = AppRoutes.Auth,
    ) {
        composable<AppRoutes.Auth> {
            // Automatically navigate to Main while authentication is not yet implemented.
            // Replace this LaunchedEffect with a real auth-state check (e.g. from a ViewModel)
            // when business logic is introduced.
            LaunchedEffect(Unit) {
                navController.navigate(AppRoutes.Main) {
                    popUpTo<AppRoutes.Auth> { inclusive = true }
                }
            }
            AuthScreen()
        }

        composable<AppRoutes.Main> {
            AdaptiveNavigationLayout()
        }
    }
}