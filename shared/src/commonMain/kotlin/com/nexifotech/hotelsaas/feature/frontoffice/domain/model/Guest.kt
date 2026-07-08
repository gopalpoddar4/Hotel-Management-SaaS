package com.nexifotech.hotelsaas.feature.frontoffice.domain.model

data class Guest(
    val id: String,
    val name: String,
    val roomNumber: String,
    val bookingStatus: String,
    val checkInStatus: String,
    val stayDuration: String
)
