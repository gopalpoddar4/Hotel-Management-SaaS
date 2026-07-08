package com.nexifotech.hotelsaas.feature.reservation.domain.model

data class ReservationSummaryMetrics(
    val totalReservations: Int,
    val todaysCheckIns: Int,
    val todaysCheckOuts: Int,
    val upcomingArrivals: Int,
    val pendingConfirmations: Int,
    val cancelledReservations: Int
)
