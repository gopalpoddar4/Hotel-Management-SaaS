package com.nexifotech.hotelsaas.feature.expenses.data.repository

import com.nexifotech.hotelsaas.feature.expenses.domain.model.ApprovalStatus
import com.nexifotech.hotelsaas.feature.expenses.domain.model.Department
import com.nexifotech.hotelsaas.feature.expenses.domain.model.Expense
import com.nexifotech.hotelsaas.feature.expenses.domain.model.ExpenseCategory
import com.nexifotech.hotelsaas.feature.expenses.domain.model.ExpenseSummary
import com.nexifotech.hotelsaas.feature.expenses.domain.model.ExpenseTimelineEvent
import com.nexifotech.hotelsaas.feature.expenses.domain.model.PaymentMethod
import com.nexifotech.hotelsaas.feature.expenses.domain.model.PaymentStatus
import com.nexifotech.hotelsaas.feature.expenses.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class DummyExpenseRepository : ExpenseRepository {

    private val dummyExpenses = MutableStateFlow(
        listOf(
            Expense(
                id = "EXP-1001",
                category = ExpenseCategory.SUPPLIES,
                vendor = "Office Depot",
                amount = 250.00,
                tax = 12.50,
                date = "2026-07-10",
                dueDate = "2026-07-25",
                paymentMethod = PaymentMethod.COMPANY_ACCOUNT,
                department = Department.FRONT_DESK,
                employeeName = "Alice Smith",
                approvalStatus = ApprovalStatus.APPROVED,
                paymentStatus = PaymentStatus.PAID,
                description = "Printer ink and paper",
                notes = "Urgent restock for the front desk.",
                receiptAvailable = true,
                createdBy = "Alice Smith",
                transactionId = "TXN-93821",
                timelineEvents = listOf(
                    ExpenseTimelineEvent("Created", "2026-07-10 09:00 AM", "Submitted by Alice Smith"),
                    ExpenseTimelineEvent("Approved", "2026-07-10 11:30 AM", "Approved by Manager"),
                    ExpenseTimelineEvent("Paid", "2026-07-11 10:00 AM", "Payment processed")
                )
            ),
            Expense(
                id = "EXP-1002",
                category = ExpenseCategory.MAINTENANCE,
                vendor = "Plumbing Pros",
                amount = 450.00,
                tax = 22.50,
                date = "2026-07-11",
                dueDate = "2026-08-11",
                paymentMethod = PaymentMethod.BANK_TRANSFER,
                department = Department.MAINTENANCE,
                employeeName = "Bob Johnson",
                approvalStatus = ApprovalStatus.PENDING,
                paymentStatus = PaymentStatus.PENDING,
                description = "Fixed leak in Room 302",
                notes = "Emergency callout.",
                receiptAvailable = true,
                createdBy = "Bob Johnson",
                transactionId = "",
                timelineEvents = listOf(
                    ExpenseTimelineEvent("Created", "2026-07-11 14:00 PM", "Submitted by Bob Johnson")
                )
            ),
            Expense(
                id = "EXP-1003",
                category = ExpenseCategory.FOOD_AND_BEVERAGE,
                vendor = "Fresh Produce Co.",
                amount = 1200.00,
                tax = 0.0,
                date = "2026-07-09",
                dueDate = "2026-07-15",
                paymentMethod = PaymentMethod.COMPANY_ACCOUNT,
                department = Department.RESTAURANT,
                employeeName = "Chef Gordon",
                approvalStatus = ApprovalStatus.APPROVED,
                paymentStatus = PaymentStatus.PENDING,
                description = "Weekly vegetable supply",
                notes = "",
                receiptAvailable = false,
                createdBy = "Chef Gordon",
                transactionId = "",
                timelineEvents = listOf(
                    ExpenseTimelineEvent("Created", "2026-07-09 06:00 AM", "Submitted by Chef Gordon"),
                    ExpenseTimelineEvent("Approved", "2026-07-09 10:00 AM", "Approved by Restaurant Manager")
                )
            ),
            Expense(
                id = "EXP-1004",
                category = ExpenseCategory.UTILITIES,
                vendor = "City Power",
                amount = 3500.00,
                tax = 175.00,
                date = "2026-07-01",
                dueDate = "2026-07-20",
                paymentMethod = PaymentMethod.BANK_TRANSFER,
                department = Department.GENERAL,
                employeeName = "System",
                approvalStatus = ApprovalStatus.APPROVED,
                paymentStatus = PaymentStatus.PAID,
                description = "Electricity bill for June",
                notes = "Auto-paid via bank transfer.",
                receiptAvailable = true,
                createdBy = "System",
                transactionId = "TXN-88221",
                timelineEvents = listOf(
                    ExpenseTimelineEvent("Generated", "2026-07-01 00:00 AM", "Auto-generated by system"),
                    ExpenseTimelineEvent("Paid", "2026-07-05 08:00 AM", "Auto-payment processed")
                )
            ),
            Expense(
                id = "EXP-1005",
                category = ExpenseCategory.MARKETING,
                vendor = "Google Ads",
                amount = 850.00,
                tax = 42.50,
                date = "2026-07-08",
                dueDate = "2026-07-08",
                paymentMethod = PaymentMethod.CREDIT_CARD,
                department = Department.MANAGEMENT,
                employeeName = "Sarah Lee",
                approvalStatus = ApprovalStatus.APPROVED,
                paymentStatus = PaymentStatus.PAID,
                description = "Summer promo campaign",
                notes = "",
                receiptAvailable = true,
                createdBy = "Sarah Lee",
                transactionId = "TXN-11993",
                timelineEvents = listOf(
                    ExpenseTimelineEvent("Created", "2026-07-08 11:00 AM", "Submitted by Sarah Lee"),
                    ExpenseTimelineEvent("Approved", "2026-07-08 12:00 PM", "Approved by GM"),
                    ExpenseTimelineEvent("Paid", "2026-07-08 12:05 PM", "Paid via Corporate Card")
                )
            )
        )
    )

    override fun getExpenses(): Flow<List<Expense>> {
        return dummyExpenses
    }

    override suspend fun getExpenseById(id: String): Expense? {
        return dummyExpenses.value.find { it.id == id }
    }

    override fun getExpenseSummary(): Flow<ExpenseSummary> {
        return dummyExpenses.map { expenses ->
            val total = expenses.sumOf { it.total }
            val pending = expenses.filter { it.paymentStatus == PaymentStatus.PENDING }.sumOf { it.total }
            val approved = expenses.filter { it.approvalStatus == ApprovalStatus.APPROVED }.sumOf { it.total }
            // Simplifying "today" and "this month" for the dummy implementation
            val today = expenses.filter { it.date == "2026-07-11" }.sumOf { it.total }
            val thisMonth = total 
            
            ExpenseSummary(
                totalExpenses = total,
                todaysExpenses = today,
                thisMonthExpenses = thisMonth,
                pendingPayments = pending,
                approvedExpenses = approved,
                reimbursableExpenses = 250.0 // Hardcoded for demo
            )
        }
    }

    override suspend fun approveExpense(id: String) {
        updateExpense(id) { it.copy(approvalStatus = ApprovalStatus.APPROVED) }
    }

    override suspend fun rejectExpense(id: String) {
         updateExpense(id) { it.copy(approvalStatus = ApprovalStatus.REJECTED) }
    }

    override suspend fun markAsPaid(id: String) {
         updateExpense(id) { it.copy(paymentStatus = PaymentStatus.PAID) }
    }

    override suspend fun addExpense(expense: Expense) {
        dummyExpenses.value = dummyExpenses.value + expense
    }

    private fun updateExpense(id: String, update: (Expense) -> Expense) {
        dummyExpenses.value = dummyExpenses.value.map {
            if (it.id == id) update(it) else it
        }
    }
}
