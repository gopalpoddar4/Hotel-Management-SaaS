package com.nexifotech.hotelsaas.feature.payroll.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.EmployeePayrollCard
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.PayrollEmptyState
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.PayrollErrorState
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.PayrollLoadingState
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.PayrollSearchAndFilter
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.PayrollSummaryGrid
import com.nexifotech.hotelsaas.feature.payroll.presentation.event.PayrollEvent
import com.nexifotech.hotelsaas.feature.payroll.presentation.viewmodel.PayrollViewModel

@Composable
fun PayrollScreen(
    onNavigateToDetails: (String) -> Unit,
    onNavigateToPayslip: (String) -> Unit,
    viewModel: PayrollViewModel = viewModel { PayrollViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading && uiState.allRecords.isEmpty()) {
        PayrollLoadingState()
        return
    }

    if (uiState.error != null && uiState.allRecords.isEmpty()) {
        PayrollErrorState(
            message = uiState.error!!,
            onRetry = { viewModel.onEvent(PayrollEvent.OnRefresh) }
        )
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Text(
                text = "Payroll",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp, start = 16.dp)
            )
        }
        if (uiState.summary != null) {
            item {
                PayrollSummaryGrid(summary = uiState.summary!!)
            }
        }

        item {
            PayrollSearchAndFilter(
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = { viewModel.onEvent(PayrollEvent.OnSearchQueryChanged(it)) },
                selectedFilter = uiState.selectedFilter,
                onFilterSelected = { viewModel.onEvent(PayrollEvent.OnFilterSelected(it)) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (uiState.filteredRecords.isEmpty() && !uiState.isLoading) {
            item {
                PayrollEmptyState("No payroll records found for the selected criteria.")
            }
        } else {
            items(uiState.filteredRecords, key = { it.id }) { record ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    EmployeePayrollCard(
                        record = record,
                        onClick = { onNavigateToDetails(record.id) },
                        onGeneratePayslip = { onNavigateToPayslip(record.id) },
                        onMarkAsPaid = {
                            // Can navigate to details or show dialog. Here we navigate to details to let user do it.
                            onNavigateToDetails(record.id)
                        }
                    )
                }
            }
        }
    }
}
