package com.nexifotech.hotelsaas.feature.payroll.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.feature.payroll.domain.model.PayrollRecord

@Composable
fun EmployeeInfoCard(
    record: PayrollRecord,
    modifier: Modifier = Modifier
) {
    DetailCard(title = "Employee Information", modifier = modifier) {
        InfoRow("Employee Name", record.employee.name)
        InfoRow("Employee ID", record.employee.id)
        InfoRow("Department", record.employee.department)
        InfoRow("Designation", record.employee.designation)
        InfoRow("Joining Date", record.employee.joiningDate)
    }
}

@Composable
fun SalaryBreakdownCard(
    record: PayrollRecord,
    modifier: Modifier = Modifier
) {
    DetailCard(title = "Salary Breakdown", modifier = modifier) {
        Text("Earnings", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))
        InfoRow("Basic Salary", "$${record.basicSalary}")
        InfoRow("House Rent Allowance", "$${record.hra}")
        InfoRow("Bonus", "$${record.bonus}")
        InfoRow("Incentives", "$${record.incentives}")
        InfoRow("Overtime Pay", "$${record.overtime}")
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        InfoRow("Total Earnings", "$${record.totalEarnings}", isBold = true)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Deductions", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(8.dp))
        InfoRow("Tax", "$${record.tax}")
        InfoRow("Provident Fund", "$${record.pf}")
        InfoRow("Other Deductions", "$${record.otherDeductions}")
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        InfoRow("Total Deductions", "$${record.totalDeductions}", isBold = true)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            InfoRow(
                label = "Net Salary", 
                value = "$${record.netSalary}", 
                isBold = true, 
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun AttendanceSummaryCard(
    record: PayrollRecord,
    modifier: Modifier = Modifier
) {
    DetailCard(title = "Attendance Summary", modifier = modifier) {
        InfoRow("Total Working Days", "${record.workingDays}")
        InfoRow("Days Present", "${record.presentDays}")
        InfoRow("Leave Taken", "${record.leaveDays}")
        InfoRow("Overtime Hours", "${record.overtimeHours} hrs")
    }
}

@Composable
fun PaymentInfoCard(
    record: PayrollRecord,
    modifier: Modifier = Modifier
) {
    DetailCard(title = "Payment Information", modifier = modifier) {
        InfoRow("Status", record.status.name)
        if (record.paymentDate.isNotEmpty()) {
            InfoRow("Payment Date", record.paymentDate)
        }
        if (record.paymentMethod.isNotEmpty()) {
            InfoRow("Payment Method", record.paymentMethod)
        }
        if (record.payslipNumber.isNotEmpty()) {
            InfoRow("Payslip Number", record.payslipNumber)
        }
    }
}

@Composable
private fun DetailCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    isBold: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
