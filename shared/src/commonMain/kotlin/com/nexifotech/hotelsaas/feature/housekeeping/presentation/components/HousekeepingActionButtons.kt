package com.nexifotech.hotelsaas.feature.housekeeping.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.TaskStatus

@Composable
fun HousekeepingActionButtons(
    status: TaskStatus,
    onStartCleaning: () -> Unit,
    onCompleteTask: () -> Unit,
    onRequestInspection: () -> Unit,
    onReportMaintenance: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        when (status) {
            TaskStatus.DIRTY -> {
                Button(
                    onClick = onStartCleaning,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                    Text(" Start Cleaning", modifier = Modifier.padding(start = 8.dp))
                }
            }
            TaskStatus.CLEANING -> {
                Button(
                    onClick = onCompleteTask,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                    Text(" Complete Cleaning", modifier = Modifier.padding(start = 8.dp))
                }
            }
            TaskStatus.READY -> {
                Button(
                    onClick = onRequestInspection,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = null)
                    Text(" Request Inspection", modifier = Modifier.padding(start = 8.dp))
                }
            }
            TaskStatus.INSPECTED -> {
                // No main action needed typically, maybe just view
            }
            TaskStatus.MAINTENANCE -> {
                 Button(
                    onClick = onCompleteTask,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                    Text(" Mark Maintenance Done", modifier = Modifier.padding(start = 8.dp))
                }
            }
            TaskStatus.COMPLETED -> {
                // Done
            }
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onReportMaintenance,
                modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Default.Build, contentDescription = null)
                Text(" Maintenance", modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}
