package com.nexifotech.hotelsaas.feature.users.presentation.state

import com.nexifotech.hotelsaas.feature.users.domain.model.User
import com.nexifotech.hotelsaas.feature.users.domain.model.UserMetrics

data class UserManagementState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val users: List<User> = emptyList(),
    val metrics: UserMetrics? = null,
    val searchQuery: String = "",
    val selectedRoleFilter: String = "All",
    val selectedStatusFilter: String = "All"
)
