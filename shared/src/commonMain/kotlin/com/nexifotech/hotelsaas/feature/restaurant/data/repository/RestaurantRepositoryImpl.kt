package com.nexifotech.hotelsaas.feature.restaurant.data.repository

import com.nexifotech.hotelsaas.feature.restaurant.data.datasource.FakeRestaurantDataSource
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.MenuItem
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.Order
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.OrderStatus
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.RestaurantSummary
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.Table
import com.nexifotech.hotelsaas.feature.restaurant.domain.repository.RestaurantRepository

class RestaurantRepositoryImpl(
    private val dataSource: FakeRestaurantDataSource
) : RestaurantRepository {
    override suspend fun getRestaurantSummary(): RestaurantSummary = dataSource.getRestaurantSummary()

    override suspend fun getTables(): List<Table> = dataSource.getTables()

    override suspend fun getActiveOrders(): List<Order> = dataSource.getActiveOrders()

    override suspend fun getOrderDetails(orderId: String): Order = dataSource.getOrderDetails(orderId)

    override suspend fun getMenuCategories(): List<String> = dataSource.getMenuCategories()

    override suspend fun getMenuItems(): List<MenuItem> = dataSource.getMenuItems()

    override suspend fun searchOrders(query: String, filter: String): List<Order> {
        val orders = dataSource.getActiveOrders()
        var filtered = orders
        if (filter != "All Orders") {
            filtered = when (filter) {
                "Dine In" -> filtered.filter { it.orderType.name == "DINE_IN" }
                "Room Service" -> filtered.filter { it.orderType.name == "ROOM_SERVICE" }
                "Take Away" -> filtered.filter { it.orderType.name == "TAKE_AWAY" }
                "Preparing" -> filtered.filter { it.status.name == "PREPARING" }
                "Ready" -> filtered.filter { it.status.name == "READY" }
                "Served" -> filtered.filter { it.status.name == "SERVED" }
                "Completed" -> filtered.filter { it.status.name == "COMPLETED" }
                "Cancelled" -> filtered.filter { it.status.name == "CANCELLED" }
                else -> filtered
            }
        }
        if (query.isNotBlank()) {
            filtered = filtered.filter {
                it.orderNumber.contains(query, ignoreCase = true) ||
                it.tableNumber?.contains(query, ignoreCase = true) == true ||
                it.guestName.contains(query, ignoreCase = true)
            }
        }
        return filtered
    }

    override suspend fun searchMenuItems(query: String, category: String): List<MenuItem> {
        val items = dataSource.getMenuItems()
        var filtered = items
        if (category != "All") {
            filtered = filtered.filter { it.category.equals(category, ignoreCase = true) }
        }
        if (query.isNotBlank()) {
            filtered = filtered.filter { it.name.contains(query, ignoreCase = true) }
        }
        return filtered
    }

    override suspend fun createOrder(order: Order): String = dataSource.createOrder(order)

    override suspend fun updateOrderStatus(orderId: String, status: OrderStatus) = dataSource.updateOrderStatus(orderId, status)

    override suspend fun generateBill(orderId: String) = dataSource.generateBill(orderId)

    override suspend fun cancelOrder(orderId: String) = dataSource.cancelOrder(orderId)
}
