package com.nexifotech.hotelsaas.feature.payroll.data.datasource

import com.nexifotech.hotelsaas.feature.payroll.domain.model.Employee
import com.nexifotech.hotelsaas.feature.payroll.domain.model.PaymentStatus
import com.nexifotech.hotelsaas.feature.payroll.domain.model.PayrollRecord
import com.nexifotech.hotelsaas.feature.payroll.domain.model.PayrollSummary
import kotlinx.coroutines.delay

class FakePayrollDataSource {
    private val employees = listOf(
        Employee("E001", "Alice Smith", "Front Desk", "Manager", "2023-01-15", true),
        Employee("E002", "Bob Jones", "Housekeeping", "Staff", "2023-02-20", true),
        Employee("E003", "Charlie Brown", "Restaurant", "Chef", "2023-03-10", true),
        Employee("E004", "Diana Prince", "Management", "Director", "2022-11-01", true),
        Employee("E005", "Evan Wright", "Maintenance", "Technician", "2023-05-12", false),
        Employee("E006", "Fiona Gallagher", "Front Desk", "Receptionist", "2023-06-25", true),
        Employee("E007", "George Miller", "Housekeeping", "Staff", "2023-07-08", false)
    )

    private val records = mutableListOf(
        PayrollRecord(
            id = "PR-1001", employee = employees[0],
            basicSalary = 5000.0, hra = 1000.0, bonus = 500.0, incentives = 200.0, overtime = 0.0,
            tax = 600.0, pf = 250.0, otherDeductions = 50.0,
            workingDays = 22, presentDays = 22, leaveDays = 0, overtimeHours = 0,
            paymentDate = "2024-05-31", paymentMethod = "Bank Transfer", status = PaymentStatus.PAID, payslipNumber = "PS-0524-1001"
        ),
        PayrollRecord(
            id = "PR-1002", employee = employees[1],
            basicSalary = 3000.0, hra = 600.0, bonus = 0.0, incentives = 0.0, overtime = 150.0,
            tax = 300.0, pf = 150.0, otherDeductions = 0.0,
            workingDays = 22, presentDays = 21, leaveDays = 1, overtimeHours = 10,
            paymentDate = "2024-05-31", paymentMethod = "Cash", status = PaymentStatus.PAID, payslipNumber = "PS-0524-1002"
        ),
        PayrollRecord(
            id = "PR-1003", employee = employees[2],
            basicSalary = 4500.0, hra = 900.0, bonus = 200.0, incentives = 100.0, overtime = 300.0,
            tax = 500.0, pf = 225.0, otherDeductions = 0.0,
            workingDays = 22, presentDays = 22, leaveDays = 0, overtimeHours = 15,
            paymentDate = "", paymentMethod = "", status = PaymentStatus.PENDING, payslipNumber = ""
        ),
        PayrollRecord(
            id = "PR-1004", employee = employees[3],
            basicSalary = 8000.0, hra = 1600.0, bonus = 1000.0, incentives = 500.0, overtime = 0.0,
            tax = 1200.0, pf = 400.0, otherDeductions = 100.0,
            workingDays = 22, presentDays = 20, leaveDays = 2, overtimeHours = 0,
            paymentDate = "", paymentMethod = "", status = PaymentStatus.PROCESSING, payslipNumber = ""
        ),
        PayrollRecord(
            id = "PR-1005", employee = employees[4],
            basicSalary = 2500.0, hra = 500.0, bonus = 0.0, incentives = 0.0, overtime = 0.0,
            tax = 200.0, pf = 125.0, otherDeductions = 0.0,
            workingDays = 22, presentDays = 15, leaveDays = 7, overtimeHours = 0,
            paymentDate = "2024-05-31", paymentMethod = "Bank Transfer", status = PaymentStatus.FAILED, payslipNumber = ""
        ),
        PayrollRecord(
            id = "PR-1006", employee = employees[5],
            basicSalary = 3500.0, hra = 700.0, bonus = 100.0, incentives = 50.0, overtime = 50.0,
            tax = 350.0, pf = 175.0, otherDeductions = 0.0,
            workingDays = 22, presentDays = 22, leaveDays = 0, overtimeHours = 2,
            paymentDate = "2024-05-31", paymentMethod = "Bank Transfer", status = PaymentStatus.PAID, payslipNumber = "PS-0524-1006"
        ),
        PayrollRecord(
            id = "PR-1007", employee = employees[6],
            basicSalary = 2800.0, hra = 560.0, bonus = 0.0, incentives = 0.0, overtime = 0.0,
            tax = 250.0, pf = 140.0, otherDeductions = 0.0,
            workingDays = 22, presentDays = 22, leaveDays = 0, overtimeHours = 0,
            paymentDate = "", paymentMethod = "", status = PaymentStatus.PENDING, payslipNumber = ""
        )
    )

    suspend fun getSummary(): PayrollSummary {
        delay(500)
        val totalEmployees = employees.size
        val monthlyPayroll = records.sumOf { it.netSalary }
        val paidSalaries = records.filter { it.status == PaymentStatus.PAID }.sumOf { it.netSalary }
        val pendingPayments = records.filter { it.status == PaymentStatus.PENDING || it.status == PaymentStatus.PROCESSING || it.status == PaymentStatus.FAILED }.sumOf { it.netSalary }
        
        return PayrollSummary(totalEmployees, monthlyPayroll, paidSalaries, pendingPayments)
    }

    suspend fun getRecords(): List<PayrollRecord> {
        delay(500)
        return records
    }

    suspend fun getRecordById(id: String): PayrollRecord? {
        delay(300)
        return records.find { it.id == id }
    }

    suspend fun markPaid(id: String) {
        delay(500)
        val index = records.indexOfFirst { it.id == id }
        if (index != -1) {
            val record = records[index]
            records[index] = record.copy(
                status = PaymentStatus.PAID, 
                paymentDate = "2024-06-01", 
                paymentMethod = "Bank Transfer",
                payslipNumber = "PS-0624-${record.id.takeLast(4)}"
            )
        }
    }
}
