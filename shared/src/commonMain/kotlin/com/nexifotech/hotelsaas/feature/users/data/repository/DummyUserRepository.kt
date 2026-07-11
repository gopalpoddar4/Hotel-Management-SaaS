package com.nexifotech.hotelsaas.feature.users.data.repository

import com.nexifotech.hotelsaas.feature.users.domain.model.User
import com.nexifotech.hotelsaas.feature.users.domain.model.UserActivity
import com.nexifotech.hotelsaas.feature.users.domain.model.UserDepartment
import com.nexifotech.hotelsaas.feature.users.domain.model.UserMetrics
import com.nexifotech.hotelsaas.feature.users.domain.model.UserPermission
import com.nexifotech.hotelsaas.feature.users.domain.model.UserRole
import com.nexifotech.hotelsaas.feature.users.domain.model.UserStatus
import com.nexifotech.hotelsaas.feature.users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class DummyUserRepository : UserRepository {
    
    private val mockUsers = listOf(
        User(
            id = "USR-001",
            employeeId = "EMP-1001",
            name = "John Doe",
            username = "johndoe",
            email = "john.doe@hotelsaas.com",
            phone = "+1 555-0101",
            gender = "Male",
            designation = "General Manager",
            role = UserRole.ADMIN,
            department = UserDepartment.MANAGEMENT,
            status = UserStatus.ACTIVE,
            permissions = UserPermission.entries.toList(),
            assignedBranch = "Downtown Grand Hotel",
            createdDate = "Jan 01, 2026",
            lastLogin = "Today, 09:00 AM",
            loginAttempts = 0,
            twoFactorEnabled = true,
            passwordStatus = "Strong",
            accountLocked = false,
            lastPasswordReset = "Mar 15, 2026",
            avatarUrl = null,
            activityTimeline = listOf(
                UserActivity("ACT-1", "Logged In", "Successful login from IP 192.168.1.1", "Today, 09:00 AM"),
                UserActivity("ACT-2", "Password Changed", "User updated their password", "Mar 15, 2026"),
                UserActivity("ACT-3", "Account Created", "Account provisioned by System", "Jan 01, 2026")
            )
        ),
        User(
            id = "USR-002",
            employeeId = "EMP-1002",
            name = "Jane Smith",
            username = "janesmith",
            email = "jane.smith@hotelsaas.com",
            phone = "+1 555-0102",
            gender = "Female",
            designation = "Front Desk Manager",
            role = UserRole.MANAGER,
            department = UserDepartment.FRONT_OFFICE,
            status = UserStatus.ACTIVE,
            permissions = listOf(
                UserPermission.DASHBOARD_ACCESS,
                UserPermission.FRONT_OFFICE,
                UserPermission.RESERVATIONS,
                UserPermission.ROOMS,
                UserPermission.GUESTS,
                UserPermission.BILLING
            ),
            assignedBranch = "Downtown Grand Hotel",
            createdDate = "Feb 10, 2026",
            lastLogin = "Today, 07:30 AM",
            loginAttempts = 0,
            twoFactorEnabled = true,
            passwordStatus = "Good",
            accountLocked = false,
            lastPasswordReset = "Feb 10, 2026",
            avatarUrl = null,
            activityTimeline = listOf(
                UserActivity("ACT-4", "Logged In", "Successful login from IP 192.168.1.5", "Today, 07:30 AM"),
                UserActivity("ACT-5", "Account Created", "Account provisioned by Admin", "Feb 10, 2026")
            )
        ),
        User(
            id = "USR-003",
            employeeId = "EMP-1003",
            name = "Michael Johnson",
            username = "mjohnson",
            email = "michael.j@hotelsaas.com",
            phone = "+1 555-0103",
            gender = "Male",
            designation = "Housekeeping Supervisor",
            role = UserRole.STAFF,
            department = UserDepartment.HOUSEKEEPING,
            status = UserStatus.ACTIVE,
            permissions = listOf(
                UserPermission.DASHBOARD_ACCESS,
                UserPermission.HOUSEKEEPING,
                UserPermission.ROOMS
            ),
            assignedBranch = "Downtown Grand Hotel",
            createdDate = "Mar 05, 2026",
            lastLogin = "Yesterday, 08:00 AM",
            loginAttempts = 0,
            twoFactorEnabled = false,
            passwordStatus = "Good",
            accountLocked = false,
            lastPasswordReset = "Mar 05, 2026",
            avatarUrl = null,
            activityTimeline = listOf(
                UserActivity("ACT-6", "Logged In", "Successful login from IP 192.168.1.12", "Yesterday, 08:00 AM"),
                UserActivity("ACT-7", "Account Created", "Account provisioned by Admin", "Mar 05, 2026")
            )
        ),
        User(
            id = "USR-004",
            employeeId = "EMP-1004",
            name = "Emily Davis",
            username = "edavis",
            email = "emily.d@hotelsaas.com",
            phone = "+1 555-0104",
            gender = "Female",
            designation = "Receptionist",
            role = UserRole.RECEPTIONIST,
            department = UserDepartment.FRONT_OFFICE,
            status = UserStatus.INACTIVE,
            permissions = listOf(
                UserPermission.DASHBOARD_ACCESS,
                UserPermission.FRONT_OFFICE,
                UserPermission.RESERVATIONS,
                UserPermission.GUESTS
            ),
            assignedBranch = "Downtown Grand Hotel",
            createdDate = "Apr 12, 2026",
            lastLogin = "2 weeks ago",
            loginAttempts = 0,
            twoFactorEnabled = false,
            passwordStatus = "Needs Update",
            accountLocked = false,
            lastPasswordReset = "Apr 12, 2026",
            avatarUrl = null,
            activityTimeline = listOf(
                UserActivity("ACT-8", "Account Deactivated", "User marked as Inactive", "1 week ago")
            )
        ),
        User(
            id = "USR-005",
            employeeId = "EMP-1005",
            name = "Robert Wilson",
            username = "rwilson",
            email = "robert.w@hotelsaas.com",
            phone = "+1 555-0105",
            gender = "Male",
            designation = "Chief Accountant",
            role = UserRole.ACCOUNTANT,
            department = UserDepartment.FINANCE,
            status = UserStatus.SUSPENDED,
            permissions = listOf(
                UserPermission.DASHBOARD_ACCESS,
                UserPermission.BILLING,
                UserPermission.PAYROLL,
                UserPermission.EXPENSES,
                UserPermission.REPORTS
            ),
            assignedBranch = "Downtown Grand Hotel",
            createdDate = "May 20, 2026",
            lastLogin = "1 month ago",
            loginAttempts = 5,
            twoFactorEnabled = true,
            passwordStatus = "Expired",
            accountLocked = true,
            lastPasswordReset = "May 20, 2026",
            avatarUrl = null,
            activityTimeline = listOf(
                UserActivity("ACT-9", "Account Locked", "Locked due to multiple failed login attempts", "1 month ago")
            )
        )
    )

    override fun getUsers(): Flow<List<User>> = flowOf(mockUsers)

    override fun getUserById(userId: String): Flow<User?> = flowOf(
        mockUsers.find { it.id == userId }
    )

    override fun getUserMetrics(): Flow<UserMetrics> {
        val metrics = UserMetrics(
            totalUsers = mockUsers.size,
            activeUsers = mockUsers.count { it.status == UserStatus.ACTIVE },
            inactiveUsers = mockUsers.count { it.status == UserStatus.INACTIVE },
            administrators = mockUsers.count { it.role == UserRole.ADMIN },
            staffMembers = mockUsers.count { it.role == UserRole.STAFF || it.role == UserRole.RECEPTIONIST || it.role == UserRole.HOUSEKEEPER || it.role == UserRole.MAINTENANCE },
            onlineUsers = 2, // Dummy value
            usersByDepartment = mockUsers.groupBy { it.department }.mapValues { it.value.size },
            usersByRole = mockUsers.groupBy { it.role }.mapValues { it.value.size }
        )
        return flowOf(metrics)
    }
}
