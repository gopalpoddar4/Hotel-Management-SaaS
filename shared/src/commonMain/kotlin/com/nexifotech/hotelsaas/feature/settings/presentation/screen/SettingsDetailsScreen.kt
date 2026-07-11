package com.nexifotech.hotelsaas.feature.settings.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.feature.settings.data.repository.DummySettingsRepository
import com.nexifotech.hotelsaas.feature.settings.domain.usecase.GetSettingsUseCase
import com.nexifotech.hotelsaas.feature.settings.domain.usecase.ResetSettingsUseCase
import com.nexifotech.hotelsaas.feature.settings.domain.usecase.SaveSettingsUseCase
import com.nexifotech.hotelsaas.feature.settings.domain.usecase.SettingsUseCases
import com.nexifotech.hotelsaas.feature.settings.presentation.event.SettingsEvent
import com.nexifotech.hotelsaas.feature.settings.presentation.screen.details.*
import com.nexifotech.hotelsaas.feature.settings.presentation.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDetailsScreen(
    categoryId: String,
    onBackClick: () -> Unit
) {
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

    var showConfirmationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.saveSuccess) {
        if (uiState.saveSuccess) {
            // Ideally show a snackbar here, then clear success state
            viewModel.onEvent(SettingsEvent.ClearSaveSuccess)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = categoryId,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (uiState.settings != null) {
                           // Show confirmation if there are unsaved changes, for now just go back
                           onBackClick()
                        } else {
                           onBackClick()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                    scrolledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                )
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else if (uiState.error != null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text(text = uiState.error ?: "Unknown error", color = MaterialTheme.colorScheme.error)
            }
        } else if (uiState.settings != null) {
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
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (categoryId) {
                    "Hotel Profile" -> HotelSettingsCard(uiState.settings!!, viewModel::onEvent)
                    "General" -> GeneralSettingsCard(uiState.settings!!, viewModel::onEvent)
                    "Appearance" -> AppearanceSettingsCard(uiState.settings!!, viewModel::onEvent)
                    "Billing" -> BillingSettingsCard(uiState.settings!!, viewModel::onEvent)
                    "Reservation" -> ReservationSettingsCard(uiState.settings!!, viewModel::onEvent)
                    "Notification" -> NotificationSettingsCard(uiState.settings!!, viewModel::onEvent)
                    "Email & SMS" -> EmailSmsSettingsCard(uiState.settings!!, viewModel::onEvent)
                    "Printer" -> PrinterSettingsCard(uiState.settings!!, viewModel::onEvent)
                    "Security" -> SecuritySettingsCard(uiState.settings!!, viewModel::onEvent)
                    "Backup" -> BackupSettingsCard(uiState.settings!!, viewModel::onEvent)
                    "System Information" -> SystemInformationCard(uiState.settings!!)
                    "About Application" -> AboutApplicationCard(uiState.settings!!)
                    else -> Text("Category not found")
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                if (categoryId != "System Information" && categoryId != "About Application") {
                    com.nexifotech.hotelsaas.feature.settings.presentation.components.ActionButtons(
                        onSave = { viewModel.onEvent(SettingsEvent.SaveSettings) },
                        onReset = { showConfirmationDialog = true }
                    )
                }
            }
        }

        if (showConfirmationDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmationDialog = false },
                title = { Text("Reset Settings") },
                text = { Text("Are you sure you want to restore default settings? This cannot be undone.") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.onEvent(SettingsEvent.ResetSettings)
                        showConfirmationDialog = false
                    }) {
                        Text("Reset")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showConfirmationDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
