package com.nexifotech.hotelsaas.feature.expenses.presentation.state

import com.nexifotech.hotelsaas.feature.expenses.domain.model.Expense
import com.nexifotech.hotelsaas.feature.expenses.domain.model.ExpenseSummary
import com.nexifotech.hotelsaas.feature.expenses.domain.model.ExpenseCategory

data class ExpensesState(
    val isLoading: Boolean = false,
    val expenses: List<Expense> = emptyList(),
    val summary: ExpenseSummary? = null,
    val searchQuery: String = "",
    val selectedCategory: ExpenseCategory? = null,
    val error: String? = null
)
