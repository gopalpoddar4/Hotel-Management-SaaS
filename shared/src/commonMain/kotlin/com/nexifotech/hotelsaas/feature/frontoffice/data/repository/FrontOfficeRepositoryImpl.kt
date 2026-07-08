package com.nexifotech.hotelsaas.feature.frontoffice.data.repository

import com.nexifotech.hotelsaas.feature.frontoffice.data.datasource.FrontOfficeDataSource
import com.nexifotech.hotelsaas.feature.frontoffice.domain.model.FrontOfficeMetrics
import com.nexifotech.hotelsaas.feature.frontoffice.domain.model.Guest
import com.nexifotech.hotelsaas.feature.frontoffice.domain.repository.FrontOfficeRepository

class FrontOfficeRepositoryImpl(
    private val dataSource: FrontOfficeDataSource
) : FrontOfficeRepository {

    override suspend fun getDashboardSummary(): FrontOfficeMetrics {
        return dataSource.getDashboardSummary()
    }

    override suspend fun getGuests(): List<Guest> {
        return dataSource.getGuests()
    }

    override suspend fun searchGuests(query: String, filter: String): List<Guest> {
        return dataSource.searchGuests(query, filter)
    }

    override suspend fun getQuickActions(): List<String> {
        return dataSource.getQuickActions()
    }

    override suspend fun checkInGuest(guestId: String): Boolean {
        return dataSource.checkInGuest(guestId)
    }

    override suspend fun checkOutGuest(guestId: String): Boolean {
        return dataSource.checkOutGuest(guestId)
    }
}
