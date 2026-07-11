package com.nexifotech.hotelsaas.feature.users.presentation.state

import com.nexifotech.hotelsaas.feature.users.domain.model.User

data class UserDetailsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: User? = null
)
