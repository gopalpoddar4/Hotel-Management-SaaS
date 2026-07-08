package com.nexifotech.hotelsaas.feature.guests.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.feature.guests.domain.model.Guest
import com.nexifotech.hotelsaas.feature.guests.domain.model.GuestStatus
import com.nexifotech.hotelsaas.feature.guests.presentation.components.*
import com.nexifotech.hotelsaas.feature.guests.presentation.event.GuestDetailsEvent
import com.nexifotech.hotelsaas.feature.guests.presentation.viewmodel.GuestDetailsViewModel
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.adaptive.rememberWindowSizeClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestDetailsScreen(
    guestId: String,
    onBackClick: () -> Unit,
) {
    // In a real app we'd inject this via DI, but for this structure we pass the id directly
    val viewModel: GuestDetailsViewModel = viewModel(key = guestId) { GuestDetailsViewModel(guestId) }
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Guest Details", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                )
            )
        }
    ) { innerPadding ->
        if (uiState.isLoading && uiState.guest == null) {
            LoadingView(modifier = Modifier.padding(innerPadding))
        } else if (uiState.error != null && uiState.guest == null) {
            ErrorView(
                message = uiState.error ?: "Unknown error",
                onRetry = { viewModel.onEvent(GuestDetailsEvent.OnRefresh) },
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            val guest = uiState.guest ?: return@Scaffold
            
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)
                            )
                        )
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = guest.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        GuestStatusChip(status = guest.status)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Guest ID: ${guest.id}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                item {
                    if (windowSizeClass == WindowSizeClass.Expanded || windowSizeClass == WindowSizeClass.Medium) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                GuestContactInfo(guest)
                                GuestStayInfo(guest)
                            }
                            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                GuestPersonalDetails(guest)
                                GuestNotes(guest)
                                GuestDetailsActions(
                                    status = guest.status,
                                    onChangeStatus = { viewModel.onEvent(GuestDetailsEvent.OnChangeStatus(it)) }
                                )
                            }
                        }
                    } else {
                        GuestContactInfo(guest)
                        GuestStayInfo(guest)
                        GuestPersonalDetails(guest)
                        GuestNotes(guest)
                        GuestDetailsActions(
                            status = guest.status,
                            onChangeStatus = { viewModel.onEvent(GuestDetailsEvent.OnChangeStatus(it)) }
                        )
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun GuestContactInfo(guest: Guest) {
    GuestInfoCard(title = "Contact Information") {
        InfoRow("Email", guest.email)
        InfoRow("Phone", guest.phone)
        InfoRow("Address", guest.address)
        InfoRow("Nationality", guest.nationality)
    }
}

@Composable
private fun GuestStayInfo(guest: Guest) {
    GuestInfoCard(title = "Stay Information") {
        InfoRow("Current Room", guest.currentRoom ?: "Not Assigned")
        InfoRow("Room Type", guest.roomType ?: "N/A")
        InfoRow("Check-in Date", guest.checkInDate ?: "N/A")
        InfoRow("Check-out Date", guest.checkOutDate ?: "N/A")
        InfoRow("Booking Source", guest.bookingSource)
        InfoRow("Payment Status", guest.paymentStatus)
    }
}

@Composable
private fun GuestPersonalDetails(guest: Guest) {
    GuestInfoCard(title = "Personal Details") {
        InfoRow("Date of Birth", guest.dateOfBirth)
        InfoRow("Gender", guest.gender)
        InfoRow("ID Proof Type", guest.idProofType)
        InfoRow("ID Number", guest.idNumber)
        InfoRow("VIP Status", if (guest.isVip) "Yes" else "No")
    }
}

@Composable
private fun GuestNotes(guest: Guest) {
    GuestInfoCard(title = "Special Requests & Notes") {
        InfoRow("Special Requests", guest.specialRequests.ifBlank { "None" })
        InfoRow("Notes", guest.notes.ifBlank { "None" })
    }
}

@Composable
private fun GuestDetailsActions(
    status: GuestStatus,
    onChangeStatus: (GuestStatus) -> Unit
) {
    GuestInfoCard(title = "Actions") {
        if (status == GuestStatus.PENDING || status == GuestStatus.RESERVED) {
            Button(
                onClick = { onChangeStatus(GuestStatus.CHECKED_IN) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Check In")
            }
        } else if (status == GuestStatus.CHECKED_IN) {
            Button(
                onClick = { onChangeStatus(GuestStatus.CHECKED_OUT) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Check Out")
            }
        }
        
        OutlinedButton(
            onClick = { /* Mock Edit */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Edit Guest Information")
        }
    }
}
