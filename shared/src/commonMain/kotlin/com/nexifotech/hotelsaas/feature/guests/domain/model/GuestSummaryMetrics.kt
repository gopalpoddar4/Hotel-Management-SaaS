package com.nexifotech.hotelsaas.feature.guests.domain.model

data class GuestSummaryMetrics(
    val totalGuests: Int,
    val checkedIn: Int,
    val checkedOut: Int,
    val vipGuests: Int,
    val newToday: Int,
    val repeatGuests: Int
)
