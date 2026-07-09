package com.nexifotech.hotelsaas.feature.restaurant.data.datasource

import com.nexifotech.hotelsaas.feature.restaurant.domain.model.*
import kotlinx.coroutines.delay

class FakeRestaurantDataSource {

    private val menuItems = listOf(
        MenuItem("M1", "Grilled Salmon", "Mains", 24.99, true, "20 mins"),
        MenuItem("M2", "Caesar Salad", "Starters", 12.99, true, "10 mins"),
        MenuItem("M3", "Beef Steak", "Mains", 34.99, true, "25 mins"),
        MenuItem("M4", "Margherita Pizza", "Mains", 18.99, true, "15 mins"),
        MenuItem("M5", "Chocolate Lava Cake", "Desserts", 9.99, true, "10 mins"),
        MenuItem("M6", "Fresh Lemonade", "Drinks", 4.99, true, "5 mins"),
        MenuItem("M7", "Cappuccino", "Drinks", 5.99, true, "5 mins")
    )

    private val tables = mutableListOf(
        Table("T1", "01", 4, TableStatus.OCCUPIED, "ORD-101", "John Doe"),
        Table("T2", "02", 2, TableStatus.AVAILABLE),
        Table("T3", "03", 6, TableStatus.RESERVED),
        Table("T4", "04", 4, TableStatus.CLEANING),
        Table("T5", "05", 2, TableStatus.AVAILABLE),
        Table("T6", "06", 8, TableStatus.OCCUPIED, "ORD-102", "Jane Smith"),
        Table("T7", "07", 4, TableStatus.AVAILABLE),
        Table("T8", "08", 2, TableStatus.AVAILABLE)
    )

    private val activeOrders = mutableListOf(
        Order(
            id = "ORD-101",
            orderNumber = "ORD-101",
            tableNumber = "01",
            guestName = "Michael Scott",
            roomNumber = null,
            orderType = OrderType.DINE_IN,
            orderTime = "12:30 PM",
            items = listOf(
                OrderItem("OI1", menuItems[0], 2, 49.98),
                OrderItem("OI2", menuItems[5], 2, 9.98)
            ),
            subtotal = 59.96,
            taxes = 5.00,
            discount = 0.0,
            grandTotal = 64.96,
            status = OrderStatus.PREPARING,
            paymentStatus = PaymentStatus.PENDING,
            assignedStaff = "John Doe",
            orderNotes = "No onions in salmon"
        ),
        Order(
            id = "ORD-102",
            orderNumber = "ORD-102",
            tableNumber = "06",
            guestName = "Jim Halpert",
            roomNumber = null,
            orderType = OrderType.DINE_IN,
            orderTime = "1:00 PM",
            items = listOf(
                OrderItem("OI3", menuItems[2], 1, 34.99),
                OrderItem("OI4", menuItems[1], 1, 12.99),
                OrderItem("OI5", menuItems[6], 2, 11.98)
            ),
            subtotal = 59.96,
            taxes = 6.04,
            discount = 0.0,
            grandTotal = 66.00,
            status = OrderStatus.READY,
            paymentStatus = PaymentStatus.PENDING,
            assignedStaff = "Jane Smith",
            orderNotes = "Medium rare steak"
        ),
        Order(
            id = "ORD-103",
            orderNumber = "ORD-103",
            tableNumber = null,
            guestName = "Pam Beesly",
            roomNumber = "204",
            orderType = OrderType.ROOM_SERVICE,
            orderTime = "1:15 PM",
            items = listOf(
                OrderItem("OI6", menuItems[4], 2, 19.98)
            ),
            subtotal = 19.98,
            taxes = 1.02,
            discount = 0.0,
            grandTotal = 21.00,
            status = OrderStatus.SERVED,
            paymentStatus = PaymentStatus.PAID,
            assignedStaff = "Dwight Schrute",
            orderNotes = "Leave at door"
        )
    )

    suspend fun getRestaurantSummary(): RestaurantSummary {
        delay(500)
        return RestaurantSummary(
            activeOrders = activeOrders.size,
            occupiedTables = tables.count { it.status == TableStatus.OCCUPIED },
            availableTables = tables.count { it.status == TableStatus.AVAILABLE },
            todaysRevenue = 1250.50
        )
    }

    suspend fun getTables(): List<Table> {
        delay(500)
        return tables
    }

    suspend fun getActiveOrders(): List<Order> {
        delay(500)
        return activeOrders
    }

    suspend fun getOrderDetails(orderId: String): Order {
        delay(500)
        return activeOrders.find { it.id == orderId } ?: throw Exception("Order not found")
    }

    suspend fun getMenuCategories(): List<String> {
        delay(200)
        return listOf("All", "Starters", "Mains", "Desserts", "Drinks")
    }

    suspend fun getMenuItems(): List<MenuItem> {
        delay(500)
        return menuItems
    }

    suspend fun updateOrderStatus(orderId: String, status: OrderStatus) {
        delay(500)
        val index = activeOrders.indexOfFirst { it.id == orderId }
        if (index != -1) {
            val order = activeOrders[index]
            activeOrders[index] = order.copy(status = status)
        }
    }

    suspend fun generateBill(orderId: String) {
        delay(500)
        // Mock generation
    }

    suspend fun createOrder(order: Order): String {
        delay(500)
        activeOrders.add(order)
        return order.id
    }

    suspend fun cancelOrder(orderId: String) {
        delay(500)
        updateOrderStatus(orderId, OrderStatus.CANCELLED)
    }
}
