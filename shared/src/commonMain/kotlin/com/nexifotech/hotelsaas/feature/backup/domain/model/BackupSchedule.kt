package com.nexifotech.hotelsaas.feature.backup.domain.model

data class BackupSchedule(
    val isAutoBackupEnabled: Boolean,
    val dailySchedule: String,
    val weeklySchedule: String,
    val monthlySchedule: String,
    val retentionPolicy: String,
    val frequency: String,
    val time: String,
    val notifyOnSuccess: Boolean,
    val notifyOnFailure: Boolean
)
