package com.nexifotech.hotelsaas.feature.payroll.domain.model

data class Employee(
    val id: String,
    val name: String,
    val department: String,
    val designation: String,
    val joiningDate: String,
    val isFullTime: Boolean
)

data class PayrollSummary(
    val totalEmployees: Int,
    val monthlyPayroll: Double,
    val paidSalaries: Double,
    val pendingPayments: Double
)

enum class PaymentStatus {
    PAID, PENDING, PROCESSING, FAILED
}

data class PayrollRecord(
    val id: String,
    val employee: Employee,
    val basicSalary: Double,
    val hra: Double,
    val bonus: Double,
    val incentives: Double,
    val overtime: Double,
    val tax: Double,
    val pf: Double,
    val otherDeductions: Double,
    val workingDays: Int,
    val presentDays: Int,
    val leaveDays: Int,
    val overtimeHours: Int,
    val paymentDate: String,
    val paymentMethod: String,
    val status: PaymentStatus,
    val payslipNumber: String
) {
    val totalEarnings: Double
        get() = basicSalary + hra + bonus + incentives + overtime

    val totalDeductions: Double
        get() = tax + pf + otherDeductions

    val netSalary: Double
        get() = totalEarnings - totalDeductions
}
