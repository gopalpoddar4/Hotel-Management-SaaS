package com.nexifotech.hotelsaas.feature.dashboard.domain.model

data class DashboardMetrics(
    val todayOccupancy: Float, // e.g., 0.78f for 78%
    val todayRevenue: Double, // e.g., 52400.0
    val arrivals: Int, // e.g., 12
    val departures: Int // e.g., 8
)

data class RoomStatusMetrics(
    val available: Int,
    val occupied: Int,
    val dirty: Int,
    val maintenance: Int
)

data class RecentBooking(
    val id: String,
    val guestName: String,
    val roomNo: String,
    val roomType: String,
    val bookingStatus: BookingStatus
)

enum class BookingStatus {
    CONFIRMED, CHECKED_IN, PENDING, CANCELLED
}
