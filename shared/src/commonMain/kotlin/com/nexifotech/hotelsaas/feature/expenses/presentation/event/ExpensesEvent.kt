package com.nexifotech.hotelsaas.feature.expenses.presentation.event

import com.nexifotech.hotelsaas.feature.expenses.domain.model.ExpenseCategory

sealed interface ExpensesEvent {
    data class SearchQueryChanged(val query: String) : ExpensesEvent
    data class CategoryFilterChanged(val category: ExpenseCategory?) : ExpensesEvent
    data class ExpenseClicked(val id: String) : ExpensesEvent
    data object Refresh : ExpensesEvent
}
