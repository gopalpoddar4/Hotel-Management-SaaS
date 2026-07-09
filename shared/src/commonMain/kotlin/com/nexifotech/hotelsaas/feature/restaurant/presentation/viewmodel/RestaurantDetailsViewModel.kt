package com.nexifotech.hotelsaas.feature.restaurant.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.restaurant.data.datasource.FakeRestaurantDataSource
import com.nexifotech.hotelsaas.feature.restaurant.data.repository.RestaurantRepositoryImpl
import com.nexifotech.hotelsaas.feature.restaurant.domain.usecase.RestaurantUseCases
import com.nexifotech.hotelsaas.feature.restaurant.presentation.event.RestaurantDetailsEvent
import com.nexifotech.hotelsaas.feature.restaurant.presentation.state.RestaurantDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RestaurantDetailsViewModel(
    private val orderId: String,
    private val useCases: RestaurantUseCases = RestaurantUseCases(RestaurantRepositoryImpl(FakeRestaurantDataSource()))
) : ViewModel() {

    private val _uiState = MutableStateFlow(RestaurantDetailsUiState())
    val uiState: StateFlow<RestaurantDetailsUiState> = _uiState.asStateFlow()

    init {
        loadOrderDetails()
    }

    fun onEvent(event: RestaurantDetailsEvent) {
        when (event) {
            is RestaurantDetailsEvent.UpdateOrderStatus -> {
                updateStatus(event.status)
            }
            RestaurantDetailsEvent.GenerateBill -> {
                generateBill()
            }
            RestaurantDetailsEvent.CancelOrder -> {
                cancelOrder()
            }
            RestaurantDetailsEvent.Retry -> {
                loadOrderDetails()
            }
        }
    }

    private fun loadOrderDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val order = useCases.getOrderDetails(orderId)
                _uiState.update { it.copy(isLoading = false, order = order) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Could not load order details")
                }
            }
        }
    }

    private fun updateStatus(status: com.nexifotech.hotelsaas.feature.restaurant.domain.model.OrderStatus) {
        viewModelScope.launch {
            try {
                useCases.updateOrderStatus(orderId, status)
                loadOrderDetails()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update status") }
            }
        }
    }

    private fun generateBill() {
        viewModelScope.launch {
            try {
                useCases.generateBill(orderId)
                // Optionally show success or navigate
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to generate bill") }
            }
        }
    }

    private fun cancelOrder() {
        viewModelScope.launch {
            try {
                useCases.cancelOrder(orderId)
                loadOrderDetails()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to cancel order") }
            }
        }
    }
}
