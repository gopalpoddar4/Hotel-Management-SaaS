package com.nexifotech.hotelsaas.feature.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.dashboard.domain.model.BookingStatus
import com.nexifotech.hotelsaas.feature.dashboard.domain.model.DashboardMetrics
import com.nexifotech.hotelsaas.feature.dashboard.domain.model.RecentBooking
import com.nexifotech.hotelsaas.feature.dashboard.domain.model.RoomStatusMetrics
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            // Simulate network delay
            delay(1000)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    metrics = DashboardMetrics(
                        todayOccupancy = 0.78f,
                        todayRevenue = 52400.0,
                        arrivals = 12,
                        departures = 8
                    ),
                    roomStatus = RoomStatusMetrics(
                        available = 45,
                        occupied = 30,
                        dirty = 10,
                        maintenance = 5
                    ),
                    recentBookings = listOf(
                        RecentBooking("1", "John Doe", "101", "Deluxe", BookingStatus.CHECKED_IN),
                        RecentBooking("2", "Jane Smith", "102", "Standard", BookingStatus.CONFIRMED),
                        RecentBooking("3", "Robert Brown", "201", "Suite", BookingStatus.PENDING),
                        RecentBooking("4", "Emily Davis", "105", "Standard", BookingStatus.CANCELLED),
                        RecentBooking("5", "Michael Wilson", "305", "Deluxe", BookingStatus.CHECKED_IN)
                    )
                )
            }
        }
    }
}
