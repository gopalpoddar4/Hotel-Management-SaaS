package com.nexifotech.hotelsaas.feature.frontoffice.data.datasource

import com.nexifotech.hotelsaas.feature.frontoffice.domain.model.FrontOfficeMetrics
import com.nexifotech.hotelsaas.feature.frontoffice.domain.model.Guest
import kotlinx.coroutines.delay

class FakeFrontOfficeDataSource : FrontOfficeDataSource {

    private val mockGuests = mutableListOf(
        Guest("1", "Alice Smith", "101", "Confirmed", "Checked In", "3 Nights"),
        Guest("2", "Bob Johnson", "205", "Pending", "Expected", "1 Night"),
        Guest("3", "Charlie Brown", "310", "Confirmed", "Reserved", "5 Nights"),
        Guest("4", "Diana Prince", "402", "VIP", "Checked In", "2 Nights")
    )

    override suspend fun getDashboardSummary(): FrontOfficeMetrics {
        delay(500)
        return FrontOfficeMetrics(
            checkIns = 12,
            checkOuts = 8,
            walkIns = 4,
            pendingArrivals = 5
        )
    }

    override suspend fun getGuests(): List<Guest> {
        delay(500)
        return mockGuests.toList()
    }

    override suspend fun searchGuests(query: String, filter: String): List<Guest> {
        delay(300)
        val lowerQuery = query.lowercase()
        return mockGuests.filter { guest ->
            val matchesQuery = guest.name.lowercase().contains(lowerQuery) ||
                    guest.roomNumber.lowercase().contains(lowerQuery) ||
                    guest.bookingStatus.lowercase().contains(lowerQuery)

            val matchesFilter = when (filter) {
                "All Guests" -> true
                "Checked In" -> guest.checkInStatus == "Checked In"
                "Reserved" -> guest.checkInStatus == "Reserved" || guest.checkInStatus == "Expected"
                "Today" -> true // Assuming today matches all in this mock
                "VIP" -> guest.bookingStatus == "VIP"
                else -> true
            }

            matchesQuery && matchesFilter
        }
    }

    override suspend fun getQuickActions(): List<String> {
        delay(200)
        return listOf("Check-In Guest", "Check-Out Guest", "Walk-in Guest", "New Reservation")
    }

    override suspend fun checkInGuest(guestId: String): Boolean {
        delay(400)
        val index = mockGuests.indexOfFirst { it.id == guestId }
        if (index != -1) {
            mockGuests[index] = mockGuests[index].copy(checkInStatus = "Checked In")
            return true
        }
        return false
    }

    override suspend fun checkOutGuest(guestId: String): Boolean {
        delay(400)
        val index = mockGuests.indexOfFirst { it.id == guestId }
        if (index != -1) {
            mockGuests[index] = mockGuests[index].copy(checkInStatus = "Checked Out")
            return true
        }
        return false
    }
}
