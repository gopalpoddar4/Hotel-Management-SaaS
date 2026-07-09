package com.nexifotech.hotelsaas.feature.billing.domain.usecase

import com.nexifotech.hotelsaas.feature.billing.domain.model.Invoice
import com.nexifotech.hotelsaas.feature.billing.domain.model.PaymentStatus
import com.nexifotech.hotelsaas.feature.billing.domain.repository.BillingRepository
import kotlinx.coroutines.flow.Flow

class GetInvoicesUseCase(private val repository: BillingRepository) {
    operator fun invoke(): Flow<List<Invoice>> {
        return repository.getInvoices()
    }
}

class GetInvoiceByIdUseCase(private val repository: BillingRepository) {
    suspend operator fun invoke(id: String): Invoice? {
        return repository.getInvoiceById(id)
    }
}

class SearchInvoicesUseCase(private val repository: BillingRepository) {
    suspend operator fun invoke(query: String): List<Invoice> {
        return repository.searchInvoices(query)
    }
}

class FilterInvoicesUseCase(private val repository: BillingRepository) {
    suspend operator fun invoke(status: PaymentStatus?): List<Invoice> {
        return repository.filterInvoices(status)
    }
}

class MarkInvoicePaidUseCase(private val repository: BillingRepository) {
    suspend operator fun invoke(id: String) {
        repository.markInvoicePaid(id)
    }
}

class GetBillingSummaryUseCase(private val repository: BillingRepository) {
    suspend operator fun invoke(): BillingSummary {
        return BillingSummary(
            todaysRevenue = repository.getTodaysRevenue(),
            totalRevenue = repository.getTotalRevenue(),
            pendingPayments = repository.getPendingPayments(),
            paidInvoices = repository.getPaidInvoicesCount(),
            outstandingAmount = repository.getOutstandingAmount(),
            refundedAmount = repository.getRefundedAmount()
        )
    }
}

data class BillingSummary(
    val todaysRevenue: Double,
    val totalRevenue: Double,
    val pendingPayments: Int,
    val paidInvoices: Int,
    val outstandingAmount: Double,
    val refundedAmount: Double
)
