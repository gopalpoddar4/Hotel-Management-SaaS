package com.nexifotech.hotelsaas.feature.guests.domain.model

data class Guest(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val nationality: String,
    val currentRoom: String?,
    val roomType: String?,
    val checkInDate: String?,
    val checkOutDate: String?,
    val status: GuestStatus,
    val isVip: Boolean,
    val address: String,
    val dateOfBirth: String,
    val gender: String,
    val idProofType: String,
    val idNumber: String,
    val paymentStatus: String,
    val bookingSource: String,
    val specialRequests: String,
    val notes: String
)
