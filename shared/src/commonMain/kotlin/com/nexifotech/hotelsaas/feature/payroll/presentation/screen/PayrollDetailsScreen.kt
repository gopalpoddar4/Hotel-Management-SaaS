package com.nexifotech.hotelsaas.feature.payroll.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.feature.payroll.domain.model.PaymentStatus
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.AttendanceSummaryCard
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.EmployeeInfoCard
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.PaymentInfoCard
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.PayrollEmptyState
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.PayrollErrorState
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.PayrollLoadingState
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.SalaryBreakdownCard
import com.nexifotech.hotelsaas.feature.payroll.presentation.event.PayrollDetailsEvent
import com.nexifotech.hotelsaas.feature.payroll.presentation.viewmodel.PayrollDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayrollDetailsScreen(
    payrollId: String,
    onBackClick: () -> Unit,
    onNavigateToPayslip: (String) -> Unit,
    viewModel: PayrollDetailsViewModel = viewModel { PayrollDetailsViewModel(payrollId = payrollId) }
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payroll Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (uiState.isLoading && uiState.record == null) {
            PayrollLoadingState(modifier = Modifier.padding(innerPadding))
            return@Scaffold
        }

        if (uiState.error != null && uiState.record == null) {
            PayrollErrorState(
                message = uiState.error!!,
                onRetry = { viewModel.onEvent(PayrollDetailsEvent.OnRefresh) },
                modifier = Modifier.padding(innerPadding)
            )
            return@Scaffold
        }

        val record = uiState.record
        if (record == null) {
            PayrollEmptyState("Record not found", modifier = Modifier.padding(innerPadding))
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (record.status != PaymentStatus.PAID) {
                        OutlinedButton(
                            onClick = { viewModel.onEvent(PayrollDetailsEvent.OnMarkAsPaid) },
                            enabled = !uiState.isMarkingPaid
                        ) {
                            Text(if (uiState.isMarkingPaid) "Processing..." else "Mark as Paid")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Button(
                        onClick = { onNavigateToPayslip(record.id) }
                    ) {
                        Text("Generate Payslip")
                    }
                }
            }

            item {
                EmployeeInfoCard(record = record)
            }

            item {
                SalaryBreakdownCard(record = record)
            }

            item {
                AttendanceSummaryCard(record = record)
            }

            item {
                PaymentInfoCard(record = record)
            }
        }
    }
}
