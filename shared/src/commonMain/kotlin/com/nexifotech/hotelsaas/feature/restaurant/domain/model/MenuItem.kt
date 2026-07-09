package com.nexifotech.hotelsaas.feature.restaurant.domain.model

data class MenuItem(
    val id: String,
    val name: String,
    val category: String,
    val price: Double,
    val isAvailable: Boolean,
    val preparationTime: String // e.g., "15 mins"
)
