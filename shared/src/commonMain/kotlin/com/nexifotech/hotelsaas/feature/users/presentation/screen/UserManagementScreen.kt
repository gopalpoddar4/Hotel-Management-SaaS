package com.nexifotech.hotelsaas.feature.users.presentation.screen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.adaptive.rememberWindowSizeClass
import com.nexifotech.hotelsaas.core.ui.theme.BookingCard
import com.nexifotech.hotelsaas.core.ui.theme.Online
import com.nexifotech.hotelsaas.core.ui.theme.Offline
import com.nexifotech.hotelsaas.core.ui.theme.RevenueCard
import com.nexifotech.hotelsaas.feature.users.domain.model.UserRole
import com.nexifotech.hotelsaas.feature.users.domain.model.UserStatus
import com.nexifotech.hotelsaas.feature.users.presentation.components.UserCard
import com.nexifotech.hotelsaas.feature.users.presentation.components.UserFilterBar
import com.nexifotech.hotelsaas.feature.users.presentation.components.UserSearchBar
import com.nexifotech.hotelsaas.feature.users.presentation.components.UserSummaryCard
import com.nexifotech.hotelsaas.feature.users.presentation.viewmodel.UserManagementViewModel
import com.nexifotech.hotelsaas.feature.users.presentation.state.UserManagementState

@Composable
fun UserManagementScreen(
    onNavigateToUserDetails: (String) -> Unit,
    viewModel: UserManagementViewModel = viewModel { UserManagementViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)
                    )
                )
            )
    ) {
        if (uiState.isLoading && uiState.users.isEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        } else if (uiState.error != null) {
            Text(
                text = uiState.error ?: "Unknown error",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            UserManagementContent(
                uiState = uiState,
                windowSizeClass = windowSizeClass,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                onRoleFilterChange = viewModel::onRoleFilterChange,
                onStatusFilterChange = viewModel::onStatusFilterChange,
                onUserClick = onNavigateToUserDetails
            )
        }
    }
}

@Composable
private fun UserManagementContent(
    uiState: UserManagementState,
    windowSizeClass: WindowSizeClass,
    onSearchQueryChange: (String) -> Unit,
    onRoleFilterChange: (String) -> Unit,
    onStatusFilterChange: (String) -> Unit,
    onUserClick: (String) -> Unit
) {
    val roleFilters = listOf("All") + UserRole.entries.map { it.displayName }
    val statusFilters = listOf("All") + UserStatus.entries.map { it.displayName }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(
                text = "User Management",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp)
            )
        }

        uiState.metrics?.let { metrics ->
            item {
                if (windowSizeClass == WindowSizeClass.Compact) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            UserSummaryCard(
                                title = "Total Users",
                                value = metrics.totalUsers.toString(),
                                icon = Icons.Default.Group,
                                iconColor = RevenueCard,
                                modifier = Modifier.weight(1f)
                            )
                            UserSummaryCard(
                                title = "Active",
                                value = metrics.activeUsers.toString(),
                                icon = Icons.Default.Person,
                                iconColor = Online,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            UserSummaryCard(
                                title = "Inactive",
                                value = metrics.inactiveUsers.toString(),
                                icon = Icons.Default.PersonOff,
                                iconColor = Offline,
                                modifier = Modifier.weight(1f)
                            )
                            UserSummaryCard(
                                title = "Admins",
                                value = metrics.administrators.toString(),
                                icon = Icons.Default.Security,
                                iconColor = BookingCard,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        UserSummaryCard(
                            title = "Total Users",
                            value = metrics.totalUsers.toString(),
                            icon = Icons.Default.Group,
                            iconColor = RevenueCard,
                            modifier = Modifier.weight(1f)
                        )
                        UserSummaryCard(
                            title = "Active",
                            value = metrics.activeUsers.toString(),
                            icon = Icons.Default.Person,
                            iconColor = Online,
                            modifier = Modifier.weight(1f)
                        )
                        UserSummaryCard(
                            title = "Inactive",
                            value = metrics.inactiveUsers.toString(),
                            icon = Icons.Default.PersonOff,
                            iconColor = Offline,
                            modifier = Modifier.weight(1f)
                        )
                        UserSummaryCard(
                            title = "Admins",
                            value = metrics.administrators.toString(),
                            icon = Icons.Default.Security,
                            iconColor = BookingCard,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                UserSearchBar(
                    query = uiState.searchQuery,
                    onQueryChange = onSearchQueryChange
                )
                
                Text(
                    text = "Role Filter",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                UserFilterBar(
                    filters = roleFilters,
                    selectedFilter = uiState.selectedRoleFilter,
                    onFilterSelected = onRoleFilterChange
                )

                Text(
                    text = "Status Filter",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                UserFilterBar(
                    filters = statusFilters,
                    selectedFilter = uiState.selectedStatusFilter,
                    onFilterSelected = onStatusFilterChange
                )
            }
        }

        if (uiState.users.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No users found matching your criteria.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(uiState.users, key = { it.id }) { user ->
                UserCard(
                    user = user,
                    onClick = { onUserClick(user.id) }
                )
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
