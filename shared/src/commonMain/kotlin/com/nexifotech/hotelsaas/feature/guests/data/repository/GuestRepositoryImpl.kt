package com.nexifotech.hotelsaas.feature.guests.data.repository

import com.nexifotech.hotelsaas.feature.guests.data.datasource.GuestDataSource
import com.nexifotech.hotelsaas.feature.guests.domain.model.Guest
import com.nexifotech.hotelsaas.feature.guests.domain.model.GuestSummaryMetrics
import com.nexifotech.hotelsaas.feature.guests.domain.repository.GuestRepository

class GuestRepositoryImpl(
    private val dataSource: GuestDataSource
) : GuestRepository {

    override suspend fun getGuests(): List<Guest> {
        return dataSource.getGuests()
    }

    override suspend fun getGuestById(id: String): Guest? {
        return dataSource.getGuestById(id)
    }

    override suspend fun searchGuests(query: String, filter: String): List<Guest> {
        return dataSource.searchGuests(query, filter)
    }

    override suspend fun getDashboardSummary(): GuestSummaryMetrics {
        return dataSource.getDashboardSummary()
    }

    override suspend fun createGuest(guest: Guest) {
        dataSource.createGuest(guest)
    }

    override suspend fun updateGuest(guest: Guest) {
        dataSource.updateGuest(guest)
    }

    override suspend fun deleteGuest(id: String) {
        dataSource.deleteGuest(id)
    }
}
