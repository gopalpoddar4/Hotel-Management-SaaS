package com.nexifotech.hotelsaas.feature.restaurant.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.restaurant.data.datasource.FakeRestaurantDataSource
import com.nexifotech.hotelsaas.feature.restaurant.data.repository.RestaurantRepositoryImpl
import com.nexifotech.hotelsaas.feature.restaurant.domain.usecase.RestaurantUseCases
import com.nexifotech.hotelsaas.feature.restaurant.presentation.event.RestaurantEvent
import com.nexifotech.hotelsaas.feature.restaurant.presentation.state.RestaurantUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RestaurantViewModel(
    private val useCases: RestaurantUseCases = RestaurantUseCases(RestaurantRepositoryImpl(FakeRestaurantDataSource()))
) : ViewModel() {

    private val _uiState = MutableStateFlow(RestaurantUiState())
    val uiState: StateFlow<RestaurantUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: RestaurantEvent) {
        when (event) {
            is RestaurantEvent.OnSearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = event.query) }
                performSearch()
            }
            is RestaurantEvent.OnFilterSelected -> {
                _uiState.update { it.copy(selectedOrderFilter = event.filter) }
                performSearch()
            }
            is RestaurantEvent.OnOrderClicked -> {
                // Handled in screen for navigation
            }
            RestaurantEvent.OnRefresh -> {
                loadData()
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val summary = useCases.getRestaurantSummary()
                val tables = useCases.getTables()
                val orders = useCases.getActiveOrders()
                val menuItems = useCases.getMenuItems()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        summary = summary,
                        tables = tables,
                        allOrders = orders,
                        filteredOrders = orders,
                        menuItems = menuItems
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "An unknown error occurred")
                }
            }
        }
    }

    private fun performSearch() {
        viewModelScope.launch {
            val currentState = _uiState.value
            try {
                val filtered = useCases.searchOrders(currentState.searchQuery, currentState.selectedOrderFilter)
                _uiState.update { it.copy(filteredOrders = filtered) }
            } catch (e: Exception) {
                // Ignore search errors or log
            }
        }
    }
}
