package com.nexifotech.hotelsaas.feature.housekeeping.data.datasource

import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.HousekeepingSummary
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.HousekeepingTask
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.TaskPriority
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.TaskStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeHousekeepingDataSource : HousekeepingDataSource {

    private val tasks = MutableStateFlow(
        listOf(
            HousekeepingTask(
                id = "HK-1001",
                roomNumber = "101",
                roomType = "Deluxe Suite",
                assignedStaff = "Maria Garcia",
                taskType = "Standard Cleaning",
                priority = TaskPriority.HIGH,
                status = TaskStatus.DIRTY,
                scheduledTime = "09:00 AM",
                estimatedDuration = "45 mins",
                startedTime = null,
                completedTime = null,
                inspectionStatus = "Pending",
                maintenanceNotes = null,
                cleaningNotes = "Please refill minibar",
                specialInstructions = "Guest arriving at 12 PM",
                lastUpdated = "2 mins ago"
            ),
            HousekeepingTask(
                id = "HK-1002",
                roomNumber = "102",
                roomType = "Standard Room",
                assignedStaff = "John Smith",
                taskType = "Turn Down",
                priority = TaskPriority.MEDIUM,
                status = TaskStatus.CLEANING,
                scheduledTime = "10:30 AM",
                estimatedDuration = "30 mins",
                startedTime = "10:45 AM",
                completedTime = null,
                inspectionStatus = "Pending",
                maintenanceNotes = null,
                cleaningNotes = null,
                specialInstructions = null,
                lastUpdated = "10 mins ago"
            ),
            HousekeepingTask(
                id = "HK-1003",
                roomNumber = "205",
                roomType = "Executive Suite",
                assignedStaff = "Elena Rodriguez",
                taskType = "Deep Cleaning",
                priority = TaskPriority.HIGH,
                status = TaskStatus.MAINTENANCE,
                scheduledTime = "11:00 AM",
                estimatedDuration = "90 mins",
                startedTime = null,
                completedTime = null,
                inspectionStatus = null,
                maintenanceNotes = "AC leaking, reported to engineering",
                cleaningNotes = null,
                specialInstructions = null,
                lastUpdated = "1 hr ago"
            ),
            HousekeepingTask(
                id = "HK-1004",
                roomNumber = "301",
                roomType = "Double Room",
                assignedStaff = "Sarah Lee",
                taskType = "Standard Cleaning",
                priority = TaskPriority.LOW,
                status = TaskStatus.COMPLETED,
                scheduledTime = "08:00 AM",
                estimatedDuration = "40 mins",
                startedTime = "08:15 AM",
                completedTime = "08:55 AM",
                inspectionStatus = "Passed",
                maintenanceNotes = null,
                cleaningNotes = null,
                specialInstructions = null,
                lastUpdated = "4 hrs ago"
            ),
            HousekeepingTask(
                id = "HK-1005",
                roomNumber = "410",
                roomType = "Presidential Suite",
                assignedStaff = "Maria Garcia",
                taskType = "Deep Cleaning",
                priority = TaskPriority.HIGH,
                status = TaskStatus.READY,
                scheduledTime = "01:00 PM",
                estimatedDuration = "120 mins",
                startedTime = "01:10 PM",
                completedTime = "03:10 PM",
                inspectionStatus = "Passed",
                maintenanceNotes = null,
                cleaningNotes = "All immaculate",
                specialInstructions = "VIP guest arriving tomorrow",
                lastUpdated = "5 hrs ago"
            )
        )
    )

    override fun getTasks(): Flow<List<HousekeepingTask>> = tasks

    override fun getSummary(): Flow<HousekeepingSummary> {
        return tasks.map { currentTasks ->
            HousekeepingSummary(
                totalRooms = 50,
                cleanRooms = 35,
                dirtyRooms = currentTasks.count { it.status == TaskStatus.DIRTY },
                roomsInCleaning = currentTasks.count { it.status == TaskStatus.CLEANING },
                maintenanceRequests = currentTasks.count { it.status == TaskStatus.MAINTENANCE },
                completedToday = currentTasks.count { it.status == TaskStatus.COMPLETED || it.status == TaskStatus.READY || it.status == TaskStatus.INSPECTED },
                pendingTasks = currentTasks.count { it.status == TaskStatus.DIRTY || it.status == TaskStatus.CLEANING }
            )
        }
    }

    override suspend fun updateTaskStatus(taskId: String, newStatus: String) {
        delay(300) // Simulate network delay
        tasks.update { currentTasks ->
            currentTasks.map { task ->
                if (task.id == taskId) {
                    task.copy(status = TaskStatus.valueOf(newStatus))
                } else {
                    task
                }
            }
        }
    }
}
