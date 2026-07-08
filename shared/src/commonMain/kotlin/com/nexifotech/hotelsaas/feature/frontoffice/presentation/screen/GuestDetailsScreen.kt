package com.nexifotech.hotelsaas.feature.frontoffice.presentation.screen

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
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.adaptive.rememberWindowSizeClass
import com.nexifotech.hotelsaas.feature.frontoffice.presentation.components.*
import com.nexifotech.hotelsaas.feature.frontoffice.presentation.event.GuestDetailsEvent
import com.nexifotech.hotelsaas.feature.frontoffice.presentation.viewmodel.GuestDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestDetailsScreen(
    guestId: String,
    onBackClick: () -> Unit,
    viewModel: GuestDetailsViewModel = viewModel { GuestDetailsViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    LaunchedEffect(guestId) {
        viewModel.onEvent(GuestDetailsEvent.LoadGuest(guestId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Guest Details",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    scrolledContainerColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)
                        )
                    )
                )
        ) {
            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
                uiState.error != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = uiState.error ?: "Unknown error",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.onEvent(GuestDetailsEvent.OnRetry) }) {
                            Text("Retry")
                        }
                    }
                }
                uiState.guest != null -> {
                    val guest = uiState.guest!!
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        item {
                            GuestHeader(guest = guest)
                        }

                        if (windowSizeClass == WindowSizeClass.Expanded || windowSizeClass == WindowSizeClass.Medium) {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.weight(2f),
                                        verticalArrangement = Arrangement.spacedBy(24.dp)
                                    ) {
                                        ContactInfoCard(guest = guest)
                                        BookingInfoCard(guest = guest)
                                    }
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(24.dp)
                                    ) {
                                        PaymentInfoCard(guest = guest)
                                        GuestInfoSection(title = "Actions") {
                                            GuestActionButtons(
                                                onCheckInClicked = { viewModel.onEvent(GuestDetailsEvent.OnCheckInClicked(guest.id)) },
                                                onCheckOutClicked = { viewModel.onEvent(GuestDetailsEvent.OnCheckOutClicked(guest.id)) },
                                                onCallClicked = { viewModel.onEvent(GuestDetailsEvent.OnCallClicked(guest.id)) },
                                                onMessageClicked = { viewModel.onEvent(GuestDetailsEvent.OnMessageClicked(guest.id)) },
                                                onEditClicked = { viewModel.onEvent(GuestDetailsEvent.OnEditClicked(guest.id)) }
                                            )
                                        }
                                    }
                                }
                            }
                        } else {
                            item { ContactInfoCard(guest = guest) }
                            item { BookingInfoCard(guest = guest) }
                            item { PaymentInfoCard(guest = guest) }
                            item {
                                GuestInfoSection(title = "Actions") {
                                    GuestActionButtons(
                                        onCheckInClicked = { viewModel.onEvent(GuestDetailsEvent.OnCheckInClicked(guest.id)) },
                                        onCheckOutClicked = { viewModel.onEvent(GuestDetailsEvent.OnCheckOutClicked(guest.id)) },
                                        onCallClicked = { viewModel.onEvent(GuestDetailsEvent.OnCallClicked(guest.id)) },
                                        onMessageClicked = { viewModel.onEvent(GuestDetailsEvent.OnMessageClicked(guest.id)) },
                                        onEditClicked = { viewModel.onEvent(GuestDetailsEvent.OnEditClicked(guest.id)) }
                                    )
                                }
                            }
                        }
                        item { Spacer(modifier = Modifier.height(24.dp)) }
                    }
                }
            }
        }
    }
}

@Composable
private fun ContactInfoCard(guest: com.nexifotech.hotelsaas.feature.frontoffice.domain.model.Guest) {
    GuestInfoSection(title = "Contact Information") {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                DetailRow(label = "Phone Number", value = guest.phoneNumber)
            }
            Box(modifier = Modifier.weight(1f)) {
                DetailRow(label = "Email Address", value = guest.email)
            }
        }
        DetailRow(label = "Address", value = guest.address)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                DetailRow(label = "Nationality", value = guest.nationality)
            }
            Box(modifier = Modifier.weight(1f)) {
                DetailRow(label = "ID Proof", value = "${guest.idProofType} (${guest.idNumber})")
            }
        }
    }
}

@Composable
private fun BookingInfoCard(guest: com.nexifotech.hotelsaas.feature.frontoffice.domain.model.Guest) {
    GuestInfoSection(title = "Booking Details") {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                DetailRow(label = "Room Type", value = guest.roomType)
            }
            Box(modifier = Modifier.weight(1f)) {
                DetailRow(label = "Room Number", value = guest.roomNumber)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                DetailRow(label = "Check-in Date", value = guest.checkInDate)
            }
            Box(modifier = Modifier.weight(1f)) {
                DetailRow(label = "Check-out Date", value = guest.checkOutDate)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                DetailRow(label = "Stay Duration", value = guest.stayDuration)
            }
            Box(modifier = Modifier.weight(1f)) {
                DetailRow(label = "Occupancy", value = "${guest.adults} Adults, ${guest.children} Children")
            }
        }
        DetailRow(label = "Special Requests", value = guest.specialRequests)
    }
}

@Composable
private fun PaymentInfoCard(guest: com.nexifotech.hotelsaas.feature.frontoffice.domain.model.Guest) {
    GuestInfoSection(title = "Payment & Status") {
        DetailRowHorizontal(label = "Payment Status", value = guest.paymentStatus)
        DetailRowHorizontal(label = "Booking Source", value = guest.bookingSource)
        DetailRowHorizontal(label = "Created On", value = guest.createdDate)
        DetailRowHorizontal(label = "Last Updated", value = guest.lastUpdated)
    }
}
