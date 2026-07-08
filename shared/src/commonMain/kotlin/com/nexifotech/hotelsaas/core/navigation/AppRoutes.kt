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
    data class GuestDetails(val guestId: String) : AppRoutes()

    @Serializable
    data object Reservations : AppRoutes()

    @Serializable
    data class ReservationDetails(val reservationId: String) : AppRoutes()

    @Serializable
    data object RoomManagement : AppRoutes()

    @Serializable
    data class RoomDetails(val roomId: String) : AppRoutes()

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