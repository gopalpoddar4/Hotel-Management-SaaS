package com.nexifotech.hotelsaas.feature.users.domain.model

enum class UserRole(val displayName: String) {
    ADMIN("Administrator"),
    MANAGER("Manager"),
    STAFF("Staff Member"),
    RECEPTIONIST("Receptionist"),
    HOUSEKEEPER("Housekeeper"),
    ACCOUNTANT("Accountant"),
    MAINTENANCE("Maintenance")
}
