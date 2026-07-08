package com.nexifotech.hotelsaas.feature.reservation.presentation.state

import com.nexifotech.hotelsaas.feature.reservation.domain.model.Reservation
import com.nexifotech.hotelsaas.feature.reservation.domain.model.ReservationSummaryMetrics

data class ReservationUiState(
    val isLoading: Boolean = false,
    val metrics: ReservationSummaryMetrics? = null,
    val allReservations: List<Reservation> = emptyList(),
    val filteredReservations: List<Reservation> = emptyList(),
    val searchQuery: String = "",
    val selectedFilter: String = "All",
    val filters: List<String> = listOf("All", "Confirmed", "Pending", "Checked In", "Checked Out", "Cancelled"),
    val error: String? = null
)
