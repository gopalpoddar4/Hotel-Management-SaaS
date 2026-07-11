package com.nexifotech.hotelsaas.feature.expenses.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.feature.expenses.domain.model.ApprovalStatus
import com.nexifotech.hotelsaas.feature.expenses.domain.model.Expense
import com.nexifotech.hotelsaas.feature.expenses.domain.model.PaymentStatus
import com.nexifotech.hotelsaas.feature.expenses.presentation.components.ExpenseStatusChip
import com.nexifotech.hotelsaas.feature.expenses.presentation.event.ExpenseDetailsEvent
import com.nexifotech.hotelsaas.feature.expenses.presentation.viewmodel.ExpenseDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDetailsScreen(
    expenseId: String,
    onNavigateBack: () -> Unit,
    viewModel: ExpenseDetailsViewModel = ExpenseDetailsViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(expenseId) {
        viewModel.onEvent(ExpenseDetailsEvent.LoadExpense(expenseId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expense Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.error ?: "Error", color = MaterialTheme.colorScheme.error)
            }
        } else {
            state.expense?.let { expense ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        ExpenseHeaderSection(expense)
                    }
                    
                    item {
                        ActionButtonsSection(expense, viewModel)
                    }
                    
                    item {
                        ExpenseInformationCard(expense)
                    }

                    item {
                        PaymentInformationCard(expense)
                    }
                    
                    if (expense.notes.isNotEmpty() || expense.description.isNotEmpty()) {
                        item {
                            NotesCard(expense)
                        }
                    }

                    item {
                        ReceiptCard(expense)
                    }
                    
                    if (expense.timelineEvents.isNotEmpty()) {
                        item {
                            TimelineCard(expense)
                        }
                    }
                    
                    item { Spacer(modifier = Modifier.height(32.dp)) }
                }
            }
        }
    }
}

@Composable
private fun ExpenseHeaderSection(expense: Expense) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = expense.vendor,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$${expense.total}",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExpenseStatusChip(status = expense.approvalStatus)
            ExpenseStatusChip(status = expense.paymentStatus)
        }
    }
}

@Composable
private fun ActionButtonsSection(expense: Expense, viewModel: ExpenseDetailsViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (expense.approvalStatus == ApprovalStatus.PENDING) {
            Button(
                onClick = { viewModel.onEvent(ExpenseDetailsEvent.ApproveExpense(expense.id)) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Approve")
            }
            Button(
                onClick = { viewModel.onEvent(ExpenseDetailsEvent.RejectExpense(expense.id)) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Reject")
            }
        } else if (expense.approvalStatus == ApprovalStatus.APPROVED && expense.paymentStatus == PaymentStatus.PENDING) {
            Button(
                onClick = { viewModel.onEvent(ExpenseDetailsEvent.MarkAsPaid(expense.id)) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.Payment, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Mark as Paid")
            }
        }
    }
}

@Composable
private fun ExpenseInformationCard(expense: Expense) {
    DetailsCard(title = "Expense Information", icon = Icons.Default.Info) {
        DetailRow("Expense ID", expense.id)
        DetailRow("Category", expense.category.displayName)
        DetailRow("Department", expense.department.displayName)
        DetailRow("Created By", expense.employeeName)
        DetailRow("Date", expense.date)
        DetailRow("Subtotal", "$${expense.amount}")
        DetailRow("Tax", "$${expense.tax}")
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        DetailRow("Total", "$${expense.total}", isBold = true)
    }
}

@Composable
private fun PaymentInformationCard(expense: Expense) {
    DetailsCard(title = "Payment Information", icon = Icons.Default.Payment) {
        DetailRow("Payment Method", expense.paymentMethod.displayName)
        if (expense.dueDate.isNotEmpty()) {
            DetailRow("Due Date", expense.dueDate)
        }
        if (expense.transactionId.isNotEmpty()) {
            DetailRow("Transaction ID", expense.transactionId)
        }
    }
}

@Composable
private fun NotesCard(expense: Expense) {
    DetailsCard(title = "Notes & Description", icon = Icons.Default.Info) {
        if (expense.description.isNotEmpty()) {
            Text("Description", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(expense.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (expense.notes.isNotEmpty()) {
            Text("Internal Notes", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(expense.notes, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun ReceiptCard(expense: Expense) {
    DetailsCard(title = "Receipt", icon = Icons.Default.Receipt) {
        if (expense.receiptAvailable) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Receipt, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Receipt Preview (Dummy)", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        } else {
            Text("No receipt attached.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun TimelineCard(expense: Expense) {
    DetailsCard(title = "Timeline", icon = Icons.Default.Info) {
        expense.timelineEvents.forEachIndexed { index, event ->
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary))
                    if (index < expense.timelineEvents.size - 1) {
                        Box(modifier = Modifier.width(2.dp).height(40.dp).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)))
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(event.status, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                    Text("${event.date} - ${event.description}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun DetailsCard(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 12.dp)) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            content()
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = if (isBold) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
