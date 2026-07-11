package com.nexifotech.hotelsaas.feature.payroll.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.core.ui.adaptive.rememberWindowSizeClass
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.feature.payroll.domain.model.PayrollSummary

@Composable
fun PayrollSummaryGrid(
    summary: PayrollSummary,
    modifier: Modifier = Modifier
) {
    val windowSizeClass = rememberWindowSizeClass()
    val columns = when (windowSizeClass) {
        WindowSizeClass.Compact -> 2
        WindowSizeClass.Medium -> 2
        WindowSizeClass.Expanded -> 4
    }

    if (columns == 4) {
        Row(
            modifier = modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PayrollSummaryCard(
                title = "Total Employees",
                value = summary.totalEmployees.toString(),
                icon = Icons.Default.AccountCircle,
                modifier = Modifier.weight(1f)
            )
            PayrollSummaryCard(
                title = "Monthly Payroll",
                value = "$${summary.monthlyPayroll}",
                icon = Icons.Default.DateRange,
                modifier = Modifier.weight(1f)
            )
            PayrollSummaryCard(
                title = "Paid Salaries",
                value = "$${summary.paidSalaries}",
                icon = Icons.Default.CheckCircle,
                modifier = Modifier.weight(1f)
            )
            PayrollSummaryCard(
                title = "Pending Payments",
                value = "$${summary.pendingPayments}",
                icon = Icons.Default.Warning,
                modifier = Modifier.weight(1f)
            )
        }
    } else {
        Column(
            modifier = modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PayrollSummaryCard(
                    title = "Total Employees",
                    value = summary.totalEmployees.toString(),
                    icon = Icons.Default.AccountCircle,
                    modifier = Modifier.weight(1f)
                )
                PayrollSummaryCard(
                    title = "Monthly Payroll",
                    value = "$${summary.monthlyPayroll}",
                    icon = Icons.Default.DateRange,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PayrollSummaryCard(
                    title = "Paid Salaries",
                    value = "$${summary.paidSalaries}",
                    icon = Icons.Default.CheckCircle,
                    modifier = Modifier.weight(1f)
                )
                PayrollSummaryCard(
                    title = "Pending Payments",
                    value = "$${summary.pendingPayments}",
                    icon = Icons.Default.Warning,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun PayrollSummaryCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
