package com.nexifotech.hotelsaas.feature.expenses.presentation.state

import com.nexifotech.hotelsaas.feature.expenses.domain.model.Expense

data class ExpenseDetailsState(
    val isLoading: Boolean = true,
    val expense: Expense? = null,
    val error: String? = null
)
