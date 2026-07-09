package com.nexifotech.hotelsaas.feature.restaurant.presentation.state

import com.nexifotech.hotelsaas.feature.restaurant.domain.model.Order

data class RestaurantDetailsUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val order: Order? = null
)
