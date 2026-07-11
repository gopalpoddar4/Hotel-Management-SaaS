package com.nexifotech.hotelsaas.feature.backup.domain.model

data class StorageUsage(
    val localStorageUsed: String,
    val localStorageTotal: String,
    val localPercentage: Float,
    val cloudStorageUsed: String,
    val cloudStorageTotal: String,
    val cloudPercentage: Float,
    val databaseSize: String,
    val mediaSize: String
)
