package com.nexifotech.hotelsaas.feature.restaurant.domain.repository

import com.nexifotech.hotelsaas.feature.restaurant.domain.model.MenuItem
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.Order
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.OrderStatus
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.RestaurantSummary
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.Table

interface RestaurantRepository {
    suspend fun getRestaurantSummary(): RestaurantSummary
    suspend fun getTables(): List<Table>
    suspend fun getActiveOrders(): List<Order>
    suspend fun getOrderDetails(orderId: String): Order
    suspend fun getMenuCategories(): List<String>
    suspend fun getMenuItems(): List<MenuItem>
    suspend fun searchOrders(query: String, filter: String): List<Order>
    suspend fun searchMenuItems(query: String, category: String): List<MenuItem>
    suspend fun createOrder(order: Order): String
    suspend fun updateOrderStatus(orderId: String, status: OrderStatus)
    suspend fun generateBill(orderId: String)
    suspend fun cancelOrder(orderId: String)
}
