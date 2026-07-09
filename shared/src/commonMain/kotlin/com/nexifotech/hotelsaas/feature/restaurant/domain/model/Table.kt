package com.nexifotech.hotelsaas.feature.restaurant.domain.model

data class Table(
    val id: String,
    val number: String,
    val capacity: Int,
    val status: TableStatus,
    val currentOrderId: String? = null,
    val assignedWaiter: String? = null
)

enum class TableStatus {
    AVAILABLE,
    OCCUPIED,
    RESERVED,
    CLEANING
}
