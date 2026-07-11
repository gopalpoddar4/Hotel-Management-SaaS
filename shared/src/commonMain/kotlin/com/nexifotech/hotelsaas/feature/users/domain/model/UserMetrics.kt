package com.nexifotech.hotelsaas.feature.users.domain.model

data class UserMetrics(
    val totalUsers: Int,
    val activeUsers: Int,
    val inactiveUsers: Int,
    val administrators: Int,
    val staffMembers: Int,
    val onlineUsers: Int,
    val usersByDepartment: Map<UserDepartment, Int>,
    val usersByRole: Map<UserRole, Int>
)
