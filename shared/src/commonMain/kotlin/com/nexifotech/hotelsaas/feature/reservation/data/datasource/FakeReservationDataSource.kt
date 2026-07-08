package com.nexifotech.hotelsaas.feature.reservation.data.datasource

import com.nexifotech.hotelsaas.feature.reservation.domain.model.Reservation
import com.nexifotech.hotelsaas.feature.reservation.domain.model.ReservationSummaryMetrics
import kotlinx.coroutines.delay

class FakeReservationDataSource : ReservationDataSource {

    private val mockReservations = mutableListOf(
        Reservation("RES-001", "John Doe", "101", "Deluxe", "2023-11-01", "2023-11-05", 4, 800.0, "Direct", "Confirmed", "Paid"),
        Reservation("RES-002", "Jane Smith", "205", "Suite", "2023-11-01", "2023-11-03", 2, 600.0, "Booking.com", "Checked In", "Unpaid", specialRequests = "Late check-in"),
        Reservation("RES-003", "Alice Brown", "310", "Standard", "2023-11-02", "2023-11-06", 4, 400.0, "Expedia", "Pending", "Unpaid"),
        Reservation("RES-004", "Bob White", "402", "Deluxe", "2023-11-05", "2023-11-07", 2, 400.0, "Direct", "Cancelled", "Refunded"),
        Reservation("RES-005", "Charlie Green", "505", "Presidential", "2023-11-01", "2023-11-10", 9, 4500.0, "Direct", "Confirmed", "Paid", adults = 4, children = 2)
    )

    override suspend fun getDashboardSummary(): ReservationSummaryMetrics {
        delay(400)
        return ReservationSummaryMetrics(
            totalReservations = 125,
            todaysCheckIns = 12,
            todaysCheckOuts = 8,
            upcomingArrivals = 45,
            pendingConfirmations = 5,
            cancelledReservations = 3
        )
    }

    override suspend fun getReservations(): List<Reservation> {
        delay(500)
        return mockReservations.toList()
    }

    override suspend fun getReservationById(id: String): Reservation? {
        delay(300)
        return mockReservations.find { it.id == id }
    }

    override suspend fun searchReservations(query: String, filter: String): List<Reservation> {
        delay(400)
        val lowerQuery = query.lowercase()
        return mockReservations.filter { res ->
            val matchesQuery = res.guestName.lowercase().contains(lowerQuery) ||
                    res.id.lowercase().contains(lowerQuery) ||
                    res.phoneNumber.lowercase().contains(lowerQuery)

            val matchesFilter = when (filter) {
                "All" -> true
                "Confirmed" -> res.bookingStatus == "Confirmed"
                "Pending" -> res.bookingStatus == "Pending"
                "Checked In" -> res.bookingStatus == "Checked In"
                "Checked Out" -> res.bookingStatus == "Checked Out"
                "Cancelled" -> res.bookingStatus == "Cancelled"
                else -> true
            }

            matchesQuery && matchesFilter
        }
    }

    override suspend fun createReservation(reservation: Reservation): Boolean {
        delay(300)
        mockReservations.add(reservation)
        return true
    }

    override suspend fun updateReservation(reservation: Reservation): Boolean {
        delay(300)
        val index = mockReservations.indexOfFirst { it.id == reservation.id }
        if (index != -1) {
            mockReservations[index] = reservation
            return true
        }
        return false
    }

    override suspend fun cancelReservation(id: String): Boolean {
        delay(300)
        val index = mockReservations.indexOfFirst { it.id == id }
        if (index != -1) {
            mockReservations[index] = mockReservations[index].copy(bookingStatus = "Cancelled")
            return true
        }
        return false
    }
}
