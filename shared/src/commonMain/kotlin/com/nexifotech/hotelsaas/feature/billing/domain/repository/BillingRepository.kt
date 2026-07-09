package com.nexifotech.hotelsaas.feature.billing.domain.repository

import com.nexifotech.hotelsaas.feature.billing.domain.model.Invoice
import com.nexifotech.hotelsaas.feature.billing.domain.model.PaymentStatus
import kotlinx.coroutines.flow.Flow

interface BillingRepository {
    fun getInvoices(): Flow<List<Invoice>>
    suspend fun getInvoiceById(id: String): Invoice?
    suspend fun searchInvoices(query: String): List<Invoice>
    suspend fun filterInvoices(status: PaymentStatus?): List<Invoice>
    suspend fun markInvoicePaid(id: String)
    
    // Summary methods
    suspend fun getTodaysRevenue(): Double
    suspend fun getTotalRevenue(): Double
    suspend fun getPendingPayments(): Int
    suspend fun getPaidInvoicesCount(): Int
    suspend fun getOutstandingAmount(): Double
    suspend fun getRefundedAmount(): Double
}
