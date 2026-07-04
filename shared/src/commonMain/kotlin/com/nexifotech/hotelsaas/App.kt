package com.nexifotech.hotelsaas

import androidx.compose.runtime.Composable
import com.nexifotech.hotelsaas.core.di.coreModule
import com.nexifotech.hotelsaas.core.navigation.AppNavGraph
import com.nexifotech.hotelsaas.core.ui.theme.HotelSaasTheme
import org.koin.compose.KoinApplication

/**
 * Root composable for the Hotel SaaS application.
 *
 * Responsibilities:
 * 1. Initialise Koin DI with [coreModule] (provides [AppNavigator] singleton).
 * 2. Apply the app-wide [HotelSaasTheme] (Material 3 colour scheme + typography).
 * 3. Launch [AppNavGraph] which hosts the full navigation graph.
 *
 * All platform entry points (Android [MainActivity], Desktop [main], Web [main])
 * call this single composable — no platform-specific navigation setup required.
 */
@Composable
fun App() {
    KoinApplication(application = {
        modules(coreModule)
    }) {
        HotelSaasTheme {
            AppNavGraph()
        }
    }
}