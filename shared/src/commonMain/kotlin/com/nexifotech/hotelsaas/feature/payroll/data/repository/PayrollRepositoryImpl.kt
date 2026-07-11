package com.nexifotech.hotelsaas.feature.payroll.data.repository

import com.nexifotech.hotelsaas.feature.payroll.data.datasource.FakePayrollDataSource
import com.nexifotech.hotelsaas.feature.payroll.domain.model.PayrollRecord
import com.nexifotech.hotelsaas.feature.payroll.domain.model.PayrollSummary
import com.nexifotech.hotelsaas.feature.payroll.domain.repository.PayrollRepository

class PayrollRepositoryImpl(
    private val dataSource: FakePayrollDataSource
) : PayrollRepository {

    override suspend fun getPayrollSummary(): PayrollSummary {
        return dataSource.getSummary()
    }

    override suspend fun getPayrollList(): List<PayrollRecord> {
        return dataSource.getRecords()
    }

    override suspend fun getPayrollDetails(id: String): PayrollRecord? {
        return dataSource.getRecordById(id)
    }

    override suspend fun searchAndFilterPayroll(query: String, filter: String): List<PayrollRecord> {
        val allRecords = dataSource.getRecords()
        return allRecords.filter { record ->
            val matchesQuery = if (query.isNotBlank()) {
                record.employee.name.contains(query, ignoreCase = true) ||
                record.employee.id.contains(query, ignoreCase = true) ||
                record.employee.department.contains(query, ignoreCase = true)
            } else true

            val matchesFilter = when (filter) {
                "All" -> true
                "Paid" -> record.status.name == "PAID"
                "Pending" -> record.status.name == "PENDING"
                "Failed" -> record.status.name == "FAILED"
                "Processing" -> record.status.name == "PROCESSING"
                "Full Time" -> record.employee.isFullTime
                "Part Time" -> !record.employee.isFullTime
                else -> true
            }
            matchesQuery && matchesFilter
        }
    }

    override suspend fun markSalaryPaid(id: String) {
        dataSource.markPaid(id)
    }

    override suspend fun generatePayslip(id: String): Boolean {
        // Dummy implementation simulating a successful generation
        return true
    }
}
