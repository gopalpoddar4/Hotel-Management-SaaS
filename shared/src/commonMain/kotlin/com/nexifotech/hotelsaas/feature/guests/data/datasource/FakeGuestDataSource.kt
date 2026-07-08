package com.nexifotech.hotelsaas.feature.guests.data.datasource

import com.nexifotech.hotelsaas.feature.guests.domain.model.Guest
import com.nexifotech.hotelsaas.feature.guests.domain.model.GuestStatus
import com.nexifotech.hotelsaas.feature.guests.domain.model.GuestSummaryMetrics
import kotlinx.coroutines.delay

class FakeGuestDataSource : GuestDataSource {
    
    private val guests = mutableListOf(
        Guest(
            id = "GST-1001",
            name = "Sarah Jenkins",
            email = "sarah.j@example.com",
            phone = "+1 555-0102",
            nationality = "United States",
            currentRoom = "101",
            roomType = "Deluxe",
            checkInDate = "2023-10-25",
            checkOutDate = "2023-10-28",
            status = GuestStatus.CHECKED_IN,
            isVip = true,
            address = "123 Ocean Drive, Miami, FL",
            dateOfBirth = "1985-04-12",
            gender = "Female",
            idProofType = "Passport",
            idNumber = "P8374920",
            paymentStatus = "Paid",
            bookingSource = "Direct",
            specialRequests = "Late check-out, Extra pillows",
            notes = "Frequent corporate guest"
        ),
        Guest(
            id = "GST-1002",
            name = "Michael Chen",
            email = "m.chen99@example.com",
            phone = "+1 555-8832",
            nationality = "Canada",
            currentRoom = "204",
            roomType = "Suite",
            checkInDate = "2023-10-26",
            checkOutDate = "2023-11-02",
            status = GuestStatus.CHECKED_IN,
            isVip = false,
            address = "45 Maple Leaf Way, Toronto",
            dateOfBirth = "1990-11-23",
            gender = "Male",
            idProofType = "Driver License",
            idNumber = "DL928374",
            paymentStatus = "Pending",
            bookingSource = "Booking.com",
            specialRequests = "Airport transfer requested",
            notes = "First time stay"
        ),
        Guest(
            id = "GST-1003",
            name = "Emma Thompson",
            email = "emma.t@example.co.uk",
            phone = "+44 7700 900123",
            nationality = "United Kingdom",
            currentRoom = null,
            roomType = "Standard",
            checkInDate = "2023-10-24",
            checkOutDate = "2023-10-26",
            status = GuestStatus.CHECKED_OUT,
            isVip = false,
            address = "72 High Street, London",
            dateOfBirth = "1978-08-05",
            gender = "Female",
            idProofType = "Passport",
            idNumber = "P3948572",
            paymentStatus = "Paid",
            bookingSource = "Expedia",
            specialRequests = "None",
            notes = ""
        ),
        Guest(
            id = "GST-1004",
            name = "Akira Sato",
            email = "akira.s@example.jp",
            phone = "+81 90-1234-5678",
            nationality = "Japan",
            currentRoom = "305",
            roomType = "Executive",
            checkInDate = "2023-10-27",
            checkOutDate = "2023-10-30",
            status = GuestStatus.RESERVED,
            isVip = true,
            address = "1-2-3 Shibuya, Tokyo",
            dateOfBirth = "1965-02-18",
            gender = "Male",
            idProofType = "Passport",
            idNumber = "P1122334",
            paymentStatus = "Paid",
            bookingSource = "Corporate",
            specialRequests = "Quiet room, High floor",
            notes = "CEO of TechCorp"
        ),
        Guest(
            id = "GST-1005",
            name = "Maria Garcia",
            email = "maria.g@example.es",
            phone = "+34 600 123 456",
            nationality = "Spain",
            currentRoom = null,
            roomType = "Double",
            checkInDate = "2023-11-05",
            checkOutDate = "2023-11-10",
            status = GuestStatus.PENDING,
            isVip = false,
            address = "Calle Mayor 10, Madrid",
            dateOfBirth = "1995-07-30",
            gender = "Female",
            idProofType = "National ID",
            idNumber = "NID88776655",
            paymentStatus = "Unpaid",
            bookingSource = "Walk-in",
            specialRequests = "Vegetarian breakfast",
            notes = "Waiting for deposit confirmation"
        )
    )

    override suspend fun getGuests(): List<Guest> {
        delay(800) // Simulate network delay
        return guests.toList()
    }

    override suspend fun getGuestById(id: String): Guest? {
        delay(500)
        return guests.find { it.id == id }
    }

    override suspend fun searchGuests(query: String, filter: String): List<Guest> {
        delay(300)
        return guests.filter { guest ->
            val matchesQuery = query.isBlank() ||
                guest.name.contains(query, ignoreCase = true) ||
                guest.id.contains(query, ignoreCase = true) ||
                guest.email.contains(query, ignoreCase = true) ||
                guest.phone.contains(query, ignoreCase = true)

            val matchesFilter = when (filter) {
                "All" -> true
                "Checked In" -> guest.status == GuestStatus.CHECKED_IN
                "Checked Out" -> guest.status == GuestStatus.CHECKED_OUT
                "VIP" -> guest.isVip
                "Reserved" -> guest.status == GuestStatus.RESERVED
                else -> true
            }

            matchesQuery && matchesFilter
        }
    }

    override suspend fun getDashboardSummary(): GuestSummaryMetrics {
        delay(400)
        return GuestSummaryMetrics(
            totalGuests = 124,
            checkedIn = 45,
            checkedOut = 12,
            vipGuests = 8,
            newToday = 15,
            repeatGuests = 32
        )
    }

    override suspend fun createGuest(guest: Guest) {
        delay(500)
        guests.add(guest)
    }

    override suspend fun updateGuest(guest: Guest) {
        delay(500)
        val index = guests.indexOfFirst { it.id == guest.id }
        if (index != -1) {
            guests[index] = guest
        }
    }

    override suspend fun deleteGuest(id: String) {
        delay(500)
        guests.removeAll { it.id == id }
    }
}
