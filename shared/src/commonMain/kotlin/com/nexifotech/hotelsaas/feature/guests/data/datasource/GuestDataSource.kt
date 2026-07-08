package com.nexifotech.hotelsaas.feature.guests.data.datasource

import com.nexifotech.hotelsaas.feature.guests.domain.model.Guest
import com.nexifotech.hotelsaas.feature.guests.domain.model.GuestSummaryMetrics

interface GuestDataSource {
    suspend fun getGuests(): List<Guest>
    suspend fun getGuestById(id: String): Guest?
    suspend fun searchGuests(query: String, filter: String): List<Guest>
    suspend fun getDashboardSummary(): GuestSummaryMetrics
    suspend fun createGuest(guest: Guest)
    suspend fun updateGuest(guest: Guest)
    suspend fun deleteGuest(id: String)
}
