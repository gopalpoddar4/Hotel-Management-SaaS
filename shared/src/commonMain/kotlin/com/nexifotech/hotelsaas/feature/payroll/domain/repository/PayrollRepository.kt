package com.nexifotech.hotelsaas.feature.payroll.domain.repository

import com.nexifotech.hotelsaas.feature.payroll.domain.model.Employee
import com.nexifotech.hotelsaas.feature.payroll.domain.model.PayrollRecord
import com.nexifotech.hotelsaas.feature.payroll.domain.model.PayrollSummary

interface PayrollRepository {
    suspend fun getPayrollSummary(): PayrollSummary
    suspend fun getPayrollList(): List<PayrollRecord>
    suspend fun getPayrollDetails(id: String): PayrollRecord?
    suspend fun searchAndFilterPayroll(query: String, filter: String): List<PayrollRecord>
    suspend fun markSalaryPaid(id: String)
    suspend fun generatePayslip(id: String): Boolean
}
