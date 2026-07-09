package com.nexifotech.hotelsaas.feature.housekeeping.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Room
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.components.HousekeepingActionButtons
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.components.TaskPriorityChip
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.components.TaskStatusChip
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.event.HousekeepingDetailsEvent
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.viewmodel.HousekeepingDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HousekeepingDetailsScreen(
    taskId: String,
    onBackClick: () -> Unit,
    viewModel: HousekeepingDetailsViewModel = viewModel { HousekeepingDetailsViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(taskId) {
        viewModel.onEvent(HousekeepingDetailsEvent.LoadTaskDetails(taskId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Details: $taskId") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null || uiState.task == null) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text(text = uiState.error ?: "Task not found", color = MaterialTheme.colorScheme.error)
            }
        } else {
            val task = uiState.task!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Header Info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Room ${task.roomNumber}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = task.roomType,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        TaskStatusChip(status = task.status)
                        Spacer(modifier = Modifier.height(4.dp))
                        TaskPriorityChip(priority = task.priority)
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))

                // Details Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        DetailRow(icon = Icons.Default.Room, title = "Task Type", value = task.taskType)
                        Spacer(modifier = Modifier.height(12.dp))
                        DetailRow(icon = Icons.Default.Person, title = "Assigned Staff", value = task.assignedStaff)
                        Spacer(modifier = Modifier.height(12.dp))
                        DetailRow(icon = Icons.Default.Event, title = "Scheduled", value = task.scheduledTime)
                        Spacer(modifier = Modifier.height(12.dp))
                        DetailRow(icon = Icons.Default.Timer, title = "Est. Duration", value = task.estimatedDuration)
                        
                        if (task.startedTime != null) {
                            Spacer(modifier = Modifier.height(12.dp))
                            DetailRow(icon = Icons.Default.Timer, title = "Started", value = task.startedTime)
                        }
                        if (task.completedTime != null) {
                            Spacer(modifier = Modifier.height(12.dp))
                            DetailRow(icon = Icons.Default.Timer, title = "Completed", value = task.completedTime)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                
                // Notes Card
                if (task.cleaningNotes != null || task.specialInstructions != null || task.maintenanceNotes != null) {
                    Text(
                        text = "Notes & Instructions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            if (task.specialInstructions != null) {
                                NoteSection("Special Instructions", task.specialInstructions)
                            }
                            if (task.cleaningNotes != null) {
                                if (task.specialInstructions != null) Spacer(modifier = Modifier.height(12.dp))
                                NoteSection("Cleaning Notes", task.cleaningNotes)
                            }
                            if (task.maintenanceNotes != null) {
                                if (task.specialInstructions != null || task.cleaningNotes != null) Spacer(modifier = Modifier.height(12.dp))
                                NoteSection("Maintenance Notes", task.maintenanceNotes, isAlert = true)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
                
                // Actions
                HousekeepingActionButtons(
                    status = task.status,
                    onStartCleaning = { viewModel.onEvent(HousekeepingDetailsEvent.StartCleaningClicked) },
                    onCompleteTask = { viewModel.onEvent(HousekeepingDetailsEvent.CompleteTaskClicked) },
                    onRequestInspection = { viewModel.onEvent(HousekeepingDetailsEvent.RequestInspectionClicked) },
                    onReportMaintenance = { viewModel.onEvent(HousekeepingDetailsEvent.ReportMaintenanceClicked) }
                )
            }
        }
    }
}

@Composable
private fun DetailRow(icon: ImageVector, title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun NoteSection(title: String, content: String, isAlert: Boolean = false) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = Icons.Default.Note,
            contentDescription = null,
            tint = if (isAlert) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = if (isAlert) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
