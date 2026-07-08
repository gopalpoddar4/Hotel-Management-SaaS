package com.nexifotech.hotelsaas.feature.guests.presentation.state

import com.nexifotech.hotelsaas.feature.guests.domain.model.Guest
import com.nexifotech.hotelsaas.feature.guests.domain.model.GuestSummaryMetrics

data class GuestUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val metrics: GuestSummaryMetrics = GuestSummaryMetrics(0, 0, 0, 0, 0, 0),
    val allGuests: List<Guest> = emptyList(),
    val filteredGuests: List<Guest> = emptyList(),
    val searchQuery: String = "",
    val selectedFilter: String = "All",
    val filters: List<String> = listOf("All", "Checked In", "Checked Out", "VIP", "Reserved")
)
