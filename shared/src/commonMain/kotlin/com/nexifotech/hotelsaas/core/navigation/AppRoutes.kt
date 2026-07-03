package com.nexifotech.hotelsaas.core.navigation

import kotlinx.serialization.Serializable

sealed class AppRoutes {

    @Serializable
    data object Auth : AppRoutes()

    @Serializable
    data object Main : AppRoutes() // The adaptive shell containing the main app modules

    // Main App Destinations
    @Serializable
    data object Dashboard : AppRoutes()

    @Serializable
    data object FrontOffice : AppRoutes()

    @Serializable
    data object Reservations : AppRoutes()

    @Serializable
    data object RoomManagement : AppRoutes()

    @Serializable
    data object GuestManagement : AppRoutes()

    @Serializable
    data object Billing : AppRoutes()

    @Serializable
    data object Housekeeping : AppRoutes()

    @Serializable
    data object Restaurant : AppRoutes()

    @Serializable
    data object Reports : AppRoutes()

    @Serializable
    data object Settings : AppRoutes()

    @Serializable
    data object Payroll : AppRoutes()

    @Serializable
    data object Expenses : AppRoutes()

    @Serializable
    data object UserManagement : AppRoutes()

    @Serializable
    data object BackupAndSecurity : AppRoutes()
}