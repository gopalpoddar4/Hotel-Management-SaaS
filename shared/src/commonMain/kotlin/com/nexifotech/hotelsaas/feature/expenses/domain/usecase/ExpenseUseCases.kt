package com.nexifotech.hotelsaas.feature.expenses.domain.usecase

import com.nexifotech.hotelsaas.feature.expenses.domain.model.Expense
import com.nexifotech.hotelsaas.feature.expenses.domain.model.ExpenseSummary
import com.nexifotech.hotelsaas.feature.expenses.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetExpensesUseCase(private val repository: ExpenseRepository) {
    operator fun invoke(): Flow<List<Expense>> {
        return repository.getExpenses()
    }
}

class GetExpenseSummaryUseCase(private val repository: ExpenseRepository) {
    operator fun invoke(): Flow<ExpenseSummary> {
        return repository.getExpenseSummary()
    }
}

class GetExpenseByIdUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(id: String): Expense? {
        return repository.getExpenseById(id)
    }
}
