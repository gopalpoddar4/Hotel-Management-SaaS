package com.nexifotech.hotelsaas.feature.restaurant.presentation.event

import com.nexifotech.hotelsaas.feature.restaurant.domain.model.OrderStatus

sealed interface RestaurantEvent {
    data class OnSearchQueryChanged(val query: String) : RestaurantEvent
    data class OnFilterSelected(val filter: String) : RestaurantEvent
    data class OnOrderClicked(val orderId: String) : RestaurantEvent
    data object OnRefresh : RestaurantEvent
}

sealed interface RestaurantDetailsEvent {
    data class UpdateOrderStatus(val status: OrderStatus) : RestaurantDetailsEvent
    data object GenerateBill : RestaurantDetailsEvent
    data object CancelOrder : RestaurantDetailsEvent
    data object Retry : RestaurantDetailsEvent
}
