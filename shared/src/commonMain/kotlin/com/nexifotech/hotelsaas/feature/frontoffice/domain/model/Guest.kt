package com.nexifotech.hotelsaas.feature.frontoffice.domain.model

data class Guest(
    val id: String,
    val name: String,
    val roomNumber: String,
    val bookingStatus: String,
    val checkInStatus: String,
    val stayDuration: String,
    // Detailed fields
    val phoneNumber: String = "+1 234 567 8900",
    val email: String = "guest@example.com",
    val address: String = "123 Main St, New York, NY 10001",
    val idProofType: String = "Passport",
    val idNumber: String = "A12345678",
    val roomType: String = "Deluxe King",
    val checkInDate: String = "2023-10-25 14:00",
    val checkOutDate: String = "2023-10-28 11:00",
    val paymentStatus: String = "Paid",
    val nationality: String = "United States",
    val specialRequests: String = "Extra towels, high floor",
    val adults: Int = 2,
    val children: Int = 0,
    val bookingSource: String = "Booking.com",
    val createdDate: String = "2023-10-01",
    val lastUpdated: String = "2023-10-25"
)
