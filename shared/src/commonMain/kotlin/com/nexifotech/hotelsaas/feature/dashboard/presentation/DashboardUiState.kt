package com.nexifotech.hotelsaas.feature.dashboard.presentation

import com.nexifotech.hotelsaas.feature.dashboard.domain.model.DashboardMetrics
import com.nexifotech.hotelsaas.feature.dashboard.domain.model.RecentBooking
import com.nexifotech.hotelsaas.feature.dashboard.domain.model.RoomStatusMetrics

data class DashboardUiState(
    val isLoading: Boolean = false,
    val metrics: DashboardMetrics? = null,
    val roomStatus: RoomStatusMetrics? = null,
    val recentBookings: List<RecentBooking> = emptyList(),
    val error: String? = null
)
