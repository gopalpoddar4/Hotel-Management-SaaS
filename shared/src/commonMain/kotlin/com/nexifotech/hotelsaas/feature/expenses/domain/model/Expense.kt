package com.nexifotech.hotelsaas.feature.expenses.domain.model

data class Expense(
    val id: String,
    val category: ExpenseCategory,
    val vendor: String,
    val amount: Double,
    val date: String, // Or use kotlinx-datetime if available, using String for simplicity
    val paymentMethod: PaymentMethod,
    val department: Department,
    val employeeName: String,
    val approvalStatus: ApprovalStatus,
    val paymentStatus: PaymentStatus,
    val description: String,
    val notes: String = "",
    val receiptAvailable: Boolean = false,
    val tax: Double = 0.0,
    val subCategory: String = "",
    val dueDate: String = "",
    val createdBy: String = "",
    val transactionId: String = "",
    val timelineEvents: List<ExpenseTimelineEvent> = emptyList()
) {
    val total: Double get() = amount + tax
}

data class ExpenseSummary(
    val totalExpenses: Double,
    val todaysExpenses: Double,
    val thisMonthExpenses: Double,
    val pendingPayments: Double,
    val approvedExpenses: Double,
    val reimbursableExpenses: Double
)

data class ExpenseTimelineEvent(
    val status: String,
    val date: String,
    val description: String
)

enum class ExpenseCategory(val displayName: String) {
    SUPPLIES("Supplies"),
    MAINTENANCE("Maintenance"),
    UTILITIES("Utilities"),
    PAYROLL("Payroll"),
    MARKETING("Marketing"),
    FOOD_AND_BEVERAGE("Food & Beverage"),
    OTHER("Other")
}

enum class PaymentStatus(val displayName: String) {
    PENDING("Pending"),
    PAID("Paid"),
    OVERDUE("Overdue"),
    FAILED("Failed")
}

enum class ApprovalStatus(val displayName: String) {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected")
}

enum class Department(val displayName: String) {
    HOUSEKEEPING("Housekeeping"),
    RESTAURANT("Restaurant"),
    FRONT_DESK("Front Desk"),
    MANAGEMENT("Management"),
    MAINTENANCE("Maintenance"),
    GENERAL("General")
}

enum class PaymentMethod(val displayName: String) {
    CASH("Cash"),
    CREDIT_CARD("Credit Card"),
    BANK_TRANSFER("Bank Transfer"),
    COMPANY_ACCOUNT("Company Account")
}
