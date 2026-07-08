package com.nexifotech.hotelsaas.feature.frontoffice.domain.repository

import com.nexifotech.hotelsaas.feature.frontoffice.domain.model.FrontOfficeMetrics
import com.nexifotech.hotelsaas.feature.frontoffice.domain.model.Guest

interface FrontOfficeRepository {
    suspend fun getDashboardSummary(): FrontOfficeMetrics
    suspend fun getGuests(): List<Guest>
    suspend fun getGuestById(id: String): Guest?
    suspend fun searchGuests(query: String, filter: String): List<Guest>
    suspend fun getQuickActions(): List<String>
    suspend fun checkInGuest(guestId: String): Boolean
    suspend fun checkOutGuest(guestId: String): Boolean
}
