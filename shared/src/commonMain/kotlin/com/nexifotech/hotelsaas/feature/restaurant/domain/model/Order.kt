package com.nexifotech.hotelsaas.feature.restaurant.domain.model

data class Order(
    val id: String,
    val orderNumber: String,
    val tableNumber: String?,
    val guestName: String,
    val roomNumber: String?,
    val orderType: OrderType,
    val orderTime: String,
    val items: List<OrderItem>,
    val subtotal: Double,
    val taxes: Double,
    val discount: Double,
    val grandTotal: Double,
    val status: OrderStatus,
    val paymentStatus: PaymentStatus,
    val assignedStaff: String,
    val orderNotes: String
)

data class OrderItem(
    val id: String,
    val menuItem: MenuItem,
    val quantity: Int,
    val price: Double
)

enum class OrderType {
    DINE_IN,
    ROOM_SERVICE,
    TAKE_AWAY
}

enum class OrderStatus {
    PREPARING,
    READY,
    SERVED,
    COMPLETED,
    CANCELLED
}

enum class PaymentStatus {
    PAID,
    PENDING
}
