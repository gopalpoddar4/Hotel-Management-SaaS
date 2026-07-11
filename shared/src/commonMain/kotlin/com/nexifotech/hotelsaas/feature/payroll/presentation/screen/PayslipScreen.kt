package com.nexifotech.hotelsaas.feature.payroll.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.PayrollEmptyState
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.PayrollErrorState
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.PayrollLoadingState
import com.nexifotech.hotelsaas.feature.payroll.presentation.components.PayslipView
import com.nexifotech.hotelsaas.feature.payroll.presentation.event.PayslipEvent
import com.nexifotech.hotelsaas.feature.payroll.presentation.viewmodel.PayslipViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayslipScreen(
    payrollId: String,
    onBackClick: () -> Unit,
    viewModel: PayslipViewModel = viewModel { PayslipViewModel(payrollId = payrollId) }
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payslip") },
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
                onRetry = { viewModel.onEvent(PayslipEvent.OnRefresh) },
                modifier = Modifier.padding(innerPadding)
            )
            return@Scaffold
        }

        val record = uiState.record
        if (record == null) {
            PayrollEmptyState("Payslip not found", modifier = Modifier.padding(innerPadding))
            return@Scaffold
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    PayslipView(record = record)
                }
            }

            // Bottom Actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(onClick = { viewModel.onEvent(PayslipEvent.OnPrint) }) {
                    Icon(Icons.Default.Print, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Print")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { viewModel.onEvent(PayslipEvent.OnDownloadPdf) }) {
                    Icon(Icons.Default.Share, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Download PDF")
                }
            }
        }
    }
}
