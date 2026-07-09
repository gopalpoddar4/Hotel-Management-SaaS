package com.nexifotech.hotelsaas.feature.housekeeping.domain.model

data class HousekeepingTask(
    val id: String,
    val roomNumber: String,
    val roomType: String,
    val assignedStaff: String,
    val taskType: String, // e.g., "Deep Cleaning", "Standard Cleaning", "Turn Down"
    val priority: TaskPriority,
    val status: TaskStatus,
    val scheduledTime: String,
    val estimatedDuration: String,
    val startedTime: String?,
    val completedTime: String?,
    val inspectionStatus: String?,
    val maintenanceNotes: String?,
    val cleaningNotes: String?,
    val specialInstructions: String?,
    val lastUpdated: String
)
