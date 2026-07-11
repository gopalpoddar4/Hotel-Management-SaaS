package com.nexifotech.hotelsaas.feature.users.domain.model

enum class UserStatus(val displayName: String) {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    SUSPENDED("Suspended"),
    LOCKED("Locked")
}
