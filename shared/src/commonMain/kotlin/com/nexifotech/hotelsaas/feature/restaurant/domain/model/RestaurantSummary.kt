package com.nexifotech.hotelsaas.feature.restaurant.domain.model

data class RestaurantSummary(
    val activeOrders: Int,
    val occupiedTables: Int,
    val availableTables: Int,
    val todaysRevenue: Double
)
