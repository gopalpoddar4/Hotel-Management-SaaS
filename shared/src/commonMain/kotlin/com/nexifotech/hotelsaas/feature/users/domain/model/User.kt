package com.nexifotech.hotelsaas.feature.users.domain.model

data class User(
    val id: String,
    val employeeId: String,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val gender: String,
    val designation: String,
    val role: UserRole,
    val department: UserDepartment,
    val status: UserStatus,
    val permissions: List<UserPermission>,
    val assignedBranch: String,
    val createdDate: String,
    val lastLogin: String,
    val loginAttempts: Int,
    val twoFactorEnabled: Boolean,
    val passwordStatus: String,
    val accountLocked: Boolean,
    val lastPasswordReset: String,
    val avatarUrl: String? = null,
    val activityTimeline: List<UserActivity> = emptyList()
)
