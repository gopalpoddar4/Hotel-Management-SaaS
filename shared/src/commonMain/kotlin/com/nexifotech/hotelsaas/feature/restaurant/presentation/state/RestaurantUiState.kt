package com.nexifotech.hotelsaas.feature.restaurant.presentation.state

import com.nexifotech.hotelsaas.feature.restaurant.domain.model.MenuItem
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.Order
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.RestaurantSummary
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.Table

data class RestaurantUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val summary: RestaurantSummary = RestaurantSummary(0, 0, 0, 0.0),
    val tables: List<Table> = emptyList(),
    val allOrders: List<Order> = emptyList(),
    val filteredOrders: List<Order> = emptyList(),
    val menuItems: List<MenuItem> = emptyList(),
    val orderFilters: List<String> = listOf("All Orders", "Dine In", "Room Service", "Take Away", "Preparing", "Ready", "Served", "Completed", "Cancelled"),
    val selectedOrderFilter: String = "All Orders",
    val searchQuery: String = ""
)
