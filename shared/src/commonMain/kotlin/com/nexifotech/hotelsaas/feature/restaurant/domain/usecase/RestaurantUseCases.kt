package com.nexifotech.hotelsaas.feature.restaurant.domain.usecase

import com.nexifotech.hotelsaas.feature.restaurant.domain.model.MenuItem
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.Order
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.OrderStatus
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.RestaurantSummary
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.Table
import com.nexifotech.hotelsaas.feature.restaurant.domain.repository.RestaurantRepository

class RestaurantUseCases(private val repository: RestaurantRepository) {
    suspend fun getRestaurantSummary(): RestaurantSummary = repository.getRestaurantSummary()
    suspend fun getTables(): List<Table> = repository.getTables()
    suspend fun getActiveOrders(): List<Order> = repository.getActiveOrders()
    suspend fun getOrderDetails(orderId: String): Order = repository.getOrderDetails(orderId)
    suspend fun getMenuCategories(): List<String> = repository.getMenuCategories()
    suspend fun getMenuItems(): List<MenuItem> = repository.getMenuItems()
    suspend fun searchOrders(query: String, filter: String): List<Order> = repository.searchOrders(query, filter)
    suspend fun searchMenuItems(query: String, category: String): List<MenuItem> = repository.searchMenuItems(query, category)
    suspend fun createOrder(order: Order): String = repository.createOrder(order)
    suspend fun updateOrderStatus(orderId: String, status: OrderStatus) = repository.updateOrderStatus(orderId, status)
    suspend fun generateBill(orderId: String) = repository.generateBill(orderId)
    suspend fun cancelOrder(orderId: String) = repository.cancelOrder(orderId)
}
