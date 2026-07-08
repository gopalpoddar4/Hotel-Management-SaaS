package com.nexifotech.hotelsaas.feature.reservation.data.repository

import com.nexifotech.hotelsaas.feature.reservation.data.datasource.ReservationDataSource
import com.nexifotech.hotelsaas.feature.reservation.domain.model.Reservation
import com.nexifotech.hotelsaas.feature.reservation.domain.model.ReservationSummaryMetrics
import com.nexifotech.hotelsaas.feature.reservation.domain.repository.ReservationRepository

class ReservationRepositoryImpl(
    private val dataSource: ReservationDataSource
) : ReservationRepository {

    override suspend fun getDashboardSummary(): ReservationSummaryMetrics {
        return dataSource.getDashboardSummary()
    }

    override suspend fun getReservations(): List<Reservation> {
        return dataSource.getReservations()
    }

    override suspend fun getReservationById(id: String): Reservation? {
        return dataSource.getReservationById(id)
    }

    override suspend fun searchReservations(query: String, filter: String): List<Reservation> {
        return dataSource.searchReservations(query, filter)
    }

    override suspend fun createReservation(reservation: Reservation): Boolean {
        return dataSource.createReservation(reservation)
    }

    override suspend fun updateReservation(reservation: Reservation): Boolean {
        return dataSource.updateReservation(reservation)
    }

    override suspend fun cancelReservation(id: String): Boolean {
        return dataSource.cancelReservation(id)
    }
}
