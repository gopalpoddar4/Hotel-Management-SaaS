package com.nexifotech.hotelsaas.feature.reservation.domain.model

data class Reservation(
    val id: String,
    val guestName: String,
    val roomNumber: String,
    val roomType: String,
    val checkInDate: String,
    val checkOutDate: String,
    val numberOfNights: Int,
    val totalAmount: Double,
    val bookingSource: String,
    val bookingStatus: String,
    val paymentStatus: String,
    val phoneNumber: String = "+1 234 567 8900",
    val email: String = "guest@example.com",
    val adults: Int = 2,
    val children: Int = 0,
    val specialRequests: String = "None"
)
