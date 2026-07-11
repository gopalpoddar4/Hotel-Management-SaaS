package com.nexifotech.hotelsaas.feature.expenses.presentation.event

sealed interface ExpenseDetailsEvent {
    data class ApproveExpense(val id: String) : ExpenseDetailsEvent
    data class RejectExpense(val id: String) : ExpenseDetailsEvent
    data class MarkAsPaid(val id: String) : ExpenseDetailsEvent
    data class LoadExpense(val id: String) : ExpenseDetailsEvent
    data object NavigateBack : ExpenseDetailsEvent
}
