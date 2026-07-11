package com.nexifotech.hotelsaas.feature.expenses.domain.repository

import com.nexifotech.hotelsaas.feature.expenses.domain.model.Expense
import com.nexifotech.hotelsaas.feature.expenses.domain.model.ExpenseSummary
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getExpenses(): Flow<List<Expense>>
    suspend fun getExpenseById(id: String): Expense?
    fun getExpenseSummary(): Flow<ExpenseSummary>
    suspend fun approveExpense(id: String)
    suspend fun rejectExpense(id: String)
    suspend fun markAsPaid(id: String)
    suspend fun addExpense(expense: Expense)
}
