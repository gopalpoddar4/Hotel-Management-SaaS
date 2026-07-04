package com.nexifotech.hotelsaas.core.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Singleton navigation coordinator. Emit events from ViewModels / use-cases;
 * [AppNavGraph] collects them and forwards to the [NavController].
 */
class AppNavigator {

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>(extraBufferCapacity = 1)
    val navigationEvents = _navigationEvents.asSharedFlow()

    fun navigateTo(route: Any) {
        _navigationEvents.tryEmit(NavigationEvent.Navigate(route))
    }

    fun navigateUp() {
        _navigationEvents.tryEmit(NavigationEvent.NavigateUp)
    }

    fun navigateAndPopUpTo(route: Any, popUpTo: Any, inclusive: Boolean = false) {
        _navigationEvents.tryEmit(NavigationEvent.NavigateAndPopUpTo(route, popUpTo, inclusive))
    }

    sealed class NavigationEvent {
        data class Navigate(val route: Any) : NavigationEvent()
        data object NavigateUp : NavigationEvent()
        data class NavigateAndPopUpTo(
            val route: Any,
            val popUpTo: Any,
            val inclusive: Boolean,
        ) : NavigationEvent()
    }
}