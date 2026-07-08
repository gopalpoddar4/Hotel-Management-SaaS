package com.nexifotech.hotelsaas.feature.frontoffice.presentation.state

import com.nexifotech.hotelsaas.feature.frontoffice.domain.model.FrontOfficeMetrics
import com.nexifotech.hotelsaas.feature.frontoffice.domain.model.Guest

data class FrontOfficeUiState(
    val isLoading: Boolean = false,
    val metrics: FrontOfficeMetrics? = null,
    val allGuests: List<Guest> = emptyList(),
    val filteredGuests: List<Guest> = emptyList(),
    val searchQuery: String = "",
    val selectedFilter: String = "All Guests",
    val filters: List<String> = listOf("All Guests", "Checked In", "Reserved", "Today", "VIP"),
    val error: String? = null
)
