package com.nexifotech.hotelsaas.feature.settings.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.adaptive.rememberWindowSizeClass
import com.nexifotech.hotelsaas.core.ui.theme.BookingCard
import com.nexifotech.hotelsaas.core.ui.theme.GuestCard
import com.nexifotech.hotelsaas.core.ui.theme.RevenueCard
import com.nexifotech.hotelsaas.core.ui.theme.RoomCard
import com.nexifotech.hotelsaas.feature.dashboard.presentation.components.StatCard
import com.nexifotech.hotelsaas.feature.settings.data.repository.DummySettingsRepository
import com.nexifotech.hotelsaas.feature.settings.domain.usecase.GetSettingsUseCase
import com.nexifotech.hotelsaas.feature.settings.domain.usecase.ResetSettingsUseCase
import com.nexifotech.hotelsaas.feature.settings.domain.usecase.SaveSettingsUseCase
import com.nexifotech.hotelsaas.feature.settings.domain.usecase.SettingsUseCases
import com.nexifotech.hotelsaas.feature.settings.presentation.components.SearchBar
import com.nexifotech.hotelsaas.feature.settings.presentation.components.SettingsCategoryCard
import com.nexifotech.hotelsaas.feature.settings.presentation.event.SettingsEvent
import com.nexifotech.hotelsaas.feature.settings.presentation.state.SettingsUiState
import com.nexifotech.hotelsaas.feature.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    onNavigateToDetails: (String) -> Unit
) {
    // Ideally use Koin for DI, but instantiating manually here to avoid Koin boilerplate changes
    val repository = remember { DummySettingsRepository() }
    val useCases = remember {
        SettingsUseCases(
            getSettings = GetSettingsUseCase(repository),
            saveSettings = SaveSettingsUseCase(repository),
            resetSettings = ResetSettingsUseCase(repository)
        )
    }
    val viewModel: SettingsViewModel = viewModel { SettingsViewModel(useCases) }
    
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else if (uiState.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = uiState.error ?: "Unknown error", color = MaterialTheme.colorScheme.error)
        }
    } else {
        SettingsContent(
            uiState = uiState,
            windowSizeClass = windowSizeClass,
            onEvent = viewModel::onEvent,
            onNavigateToDetails = onNavigateToDetails
        )
    }
}

@Composable
private fun SettingsContent(
    uiState: SettingsUiState,
    windowSizeClass: WindowSizeClass,
    onEvent: (SettingsEvent) -> Unit,
    onNavigateToDetails: (String) -> Unit
) {
    val columns = if (windowSizeClass == WindowSizeClass.Expanded) 3 else if (windowSizeClass == WindowSizeClass.Medium) 2 else 1

    Column(
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
            .padding(horizontal = 15.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp, bottom = 16.dp)
        )

        TopMetricsSection(uiState, windowSizeClass)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = { onEvent(SettingsEvent.SearchQueryChanged(it)) }
        )
        
        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.filteredCategories.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No categories found for \"${uiState.searchQuery}\"",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                contentPadding = PaddingValues(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.filteredCategories) { category ->
                    val (icon, description) = getCategoryInfo(category)
                    SettingsCategoryCard(
                        title = category,
                        description = description,
                        icon = icon,
                        onClick = { onNavigateToDetails(category) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TopMetricsSection(uiState: SettingsUiState, windowSizeClass: WindowSizeClass) {
    val metrics = uiState.settings?.dashboardMetrics ?: return

    if (windowSizeClass == WindowSizeClass.Compact) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(
                    title = "Active Users",
                    value = metrics.activeUsers.toString(),
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.Person,
                    indicatorColor = RoomCard
                )
                StatCard(
                    title = "Connected Devices",
                    value = metrics.connectedDevices.toString(),
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.Devices,
                    indicatorColor = RevenueCard
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(
                    title = "Storage Used",
                    value = "${metrics.storageUsedGB} GB",
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.Storage,
                    indicatorColor = BookingCard
                )
                StatCard(
                    title = "Security Status",
                    value = metrics.securityStatus,
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.Security,
                    indicatorColor = GuestCard
                )
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            StatCard(
                title = "Active Users",
                value = metrics.activeUsers.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Person,
                indicatorColor = RoomCard
            )
            StatCard(
                title = "Connected Devices",
                value = metrics.connectedDevices.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Devices,
                indicatorColor = RevenueCard
            )
            StatCard(
                title = "Storage Used",
                value = "${metrics.storageUsedGB} GB",
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Storage,
                indicatorColor = BookingCard
            )
            StatCard(
                title = "Security Status",
                value = metrics.securityStatus,
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Security,
                indicatorColor = GuestCard
            )
        }
    }
}

private fun getCategoryInfo(category: String) = when (category) {
    "Hotel Profile" -> Icons.Default.Business to "Manage hotel details, address, and logo."
    "General" -> Icons.Default.Settings to "Date, time, currency, and language formats."
    "Appearance" -> Icons.Default.Palette to "Customize theme, layout, and colors."
    "Billing" -> Icons.Default.Receipt to "Configure taxes, invoices, and payments."
    "Reservation" -> Icons.Default.EventSeat to "Check-in/out times and booking rules."
    "Notification" -> Icons.Default.Notifications to "Email, SMS, and push alerts."
    "Email & SMS" -> Icons.Default.Email to "SMTP settings and message templates."
    "Printer" -> Icons.Default.Print to "Configure receipt and kitchen printers."
    "Security" -> Icons.Default.Lock to "Passwords, 2FA, and device management."
    "Backup" -> Icons.Default.Backup to "Automated backups and data retention."
    "System Information" -> Icons.Default.Info to "App version, database, and system status."
    "About Application" -> Icons.Default.Info to "License, terms, and developer information."
    else -> Icons.Default.Settings to "Configuration options."
}
