package com.nexifotech.hotelsaas.feature.users.domain.model

enum class UserPermission(val displayName: String) {
    DASHBOARD_ACCESS("Dashboard Access"),
    FRONT_OFFICE("Front Office"),
    RESERVATIONS("Reservations"),
    ROOMS("Rooms"),
    GUESTS("Guests"),
    BILLING("Billing"),
    HOUSEKEEPING("Housekeeping"),
    RESTAURANT("Restaurant"),
    REPORTS("Reports"),
    PAYROLL("Payroll"),
    EXPENSES("Expenses"),
    USER_MANAGEMENT("User Management"),
    BACKUP("Backup"),
    SETTINGS("Settings")
}
