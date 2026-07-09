package com.nexifotech.hotelsaas.feature.billing.data.repository

import com.nexifotech.hotelsaas.feature.billing.data.datasource.FakeBillingDataSource
import com.nexifotech.hotelsaas.feature.billing.domain.model.Invoice
import com.nexifotech.hotelsaas.feature.billing.domain.model.PaymentStatus
import com.nexifotech.hotelsaas.feature.billing.domain.repository.BillingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class BillingRepositoryImpl(
    private val dataSource: FakeBillingDataSource
) : BillingRepository {

    override fun getInvoices(): Flow<List<Invoice>> {
        return dataSource.invoices
    }

    override suspend fun getInvoiceById(id: String): Invoice? {
        return dataSource.invoices.first().find { it.id == id }
    }

    override suspend fun searchInvoices(query: String): List<Invoice> {
        val allInvoices = dataSource.invoices.first()
        if (query.isBlank()) return allInvoices
        
        return allInvoices.filter {
            it.guestName.contains(query, ignoreCase = true) ||
            it.invoiceNumber.contains(query, ignoreCase = true) ||
            it.bookingId.contains(query, ignoreCase = true)
        }
    }

    override suspend fun filterInvoices(status: PaymentStatus?): List<Invoice> {
        val allInvoices = dataSource.invoices.first()
        if (status == null) return allInvoices
        
        return allInvoices.filter { it.paymentStatus == status }
    }

    override suspend fun markInvoicePaid(id: String) {
        dataSource.markInvoicePaid(id)
    }

    override suspend fun getTodaysRevenue(): Double {
        // Dummy implementation for today's revenue (assuming paid today)
        return dataSource.invoices.first()
            .filter { it.paymentStatus == PaymentStatus.PAID }
            .sumOf { it.paidAmount } * 0.1 // Just a dummy calculation
    }

    override suspend fun getTotalRevenue(): Double {
        return dataSource.invoices.first()
            .filter { it.paymentStatus == PaymentStatus.PAID }
            .sumOf { it.paidAmount }
    }

    override suspend fun getPendingPayments(): Int {
        return dataSource.invoices.first()
            .count { it.paymentStatus == PaymentStatus.PENDING || it.paymentStatus == PaymentStatus.OVERDUE || it.paymentStatus == PaymentStatus.PARTIALLY_PAID }
    }

    override suspend fun getPaidInvoicesCount(): Int {
        return dataSource.invoices.first()
            .count { it.paymentStatus == PaymentStatus.PAID }
    }

    override suspend fun getOutstandingAmount(): Double {
        return dataSource.invoices.first().sumOf { it.remainingAmount }
    }

    override suspend fun getRefundedAmount(): Double {
        return dataSource.invoices.first()
            .filter { it.paymentStatus == PaymentStatus.REFUNDED }
            .sumOf { it.totalAmount }
    }
}
