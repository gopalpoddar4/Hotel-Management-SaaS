package com.nexifotech.hotelsaas.feature.reservation.domain.repository

import com.nexifotech.hotelsaas.feature.reservation.domain.model.Reservation
import com.nexifotech.hotelsaas.feature.reservation.domain.model.ReservationSummaryMetrics

interface ReservationRepository {
    suspend fun getDashboardSummary(): ReservationSummaryMetrics
    suspend fun getReservations(): List<Reservation>
    suspend fun getReservationById(id: String): Reservation?
    suspend fun searchReservations(query: String, filter: String): List<Reservation>
    suspend fun createReservation(reservation: Reservation): Boolean
    suspend fun updateReservation(reservation: Reservation): Boolean
    suspend fun cancelReservation(id: String): Boolean
}
