package com.nexifotech.hotelsaas.feature.billing.data.datasource

import com.nexifotech.hotelsaas.feature.billing.domain.model.Invoice
import com.nexifotech.hotelsaas.feature.billing.domain.model.InvoiceStatus
import com.nexifotech.hotelsaas.feature.billing.domain.model.PaymentStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeBillingDataSource {
    private val _invoices = MutableStateFlow<List<Invoice>>(emptyList())
    val invoices: StateFlow<List<Invoice>> = _invoices.asStateFlow()

    init {
        _invoices.value = generateDummyInvoices()
    }

    private fun generateDummyInvoices(): List<Invoice> {
        val list = mutableListOf<Invoice>()
        for (i in 1..20) {
            val isPaid = i % 2 == 0
            val isPending = i % 3 == 0 && !isPaid
            val isOverdue = i % 5 == 0 && !isPaid && !isPending
            
            val status = when {
                isPaid -> PaymentStatus.PAID
                isPending -> PaymentStatus.PENDING
                isOverdue -> PaymentStatus.OVERDUE
                else -> PaymentStatus.PARTIALLY_PAID
            }
            
            val total = 500.0 + (i * 150)
            val paid = if (isPaid) total else if (status == PaymentStatus.PARTIALLY_PAID) total / 2 else 0.0

            list.add(
                Invoice(
                    id = "INV-2023-${1000 + i}",
                    invoiceNumber = "INV-2023-${1000 + i}",
                    guestName = "Guest Name $i",
                    guestId = "G-$i",
                    roomNumber = "${100 + i}",
                    roomId = "R-$i",
                    bookingId = "BKG-${2000 + i}",
                    checkInDate = "2023-10-01",
                    checkOutDate = "2023-10-05",
                    stayDurationDays = 4,
                    roomCharges = 400.0 + (i * 100),
                    extraCharges = 50.0,
                    restaurantCharges = 50.0 + (i * 20),
                    laundryCharges = 0.0,
                    discount = if (i % 4 == 0) 50.0 else 0.0,
                    taxPercentage = 10.0,
                    totalAmount = total,
                    paidAmount = paid,
                    paymentMethod = if (isPaid) "Credit Card" else "N/A",
                    paymentDate = if (isPaid) "2023-10-05" else null,
                    invoiceStatus = InvoiceStatus.GENERATED,
                    paymentStatus = status,
                    notes = "Thank you for your stay."
                )
            )
        }
        return list
    }

    fun markInvoicePaid(id: String) {
        _invoices.update { currentList ->
            currentList.map { invoice ->
                if (invoice.id == id) {
                    invoice.copy(
                        paymentStatus = PaymentStatus.PAID,
                        paidAmount = invoice.totalAmount,
                        paymentMethod = "Cash",
                        paymentDate = "2023-10-10" // Assuming today's date or similar
                    )
                } else {
                    invoice
                }
            }
        }
    }
}
