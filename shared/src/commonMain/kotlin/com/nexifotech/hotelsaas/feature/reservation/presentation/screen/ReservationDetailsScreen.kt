package com.nexifotech.hotelsaas.feature.reservation.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.adaptive.rememberWindowSizeClass
import com.nexifotech.hotelsaas.feature.reservation.presentation.components.*
import com.nexifotech.hotelsaas.feature.reservation.presentation.event.ReservationDetailsEvent
import com.nexifotech.hotelsaas.feature.reservation.presentation.viewmodel.ReservationDetailsViewModel
import com.nexifotech.hotelsaas.feature.reservation.domain.model.Reservation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationDetailsScreen(
    reservationId: String,
    onBackClick: () -> Unit,
    viewModel: ReservationDetailsViewModel = viewModel { ReservationDetailsViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    LaunchedEffect(reservationId) {
        viewModel.onEvent(ReservationDetailsEvent.LoadReservation(reservationId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Reservation Details", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
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
                uiState.isLoading -> LoadingView()
                uiState.error != null -> ErrorView(
                    message = uiState.error!!,
                    onRetry = { viewModel.onEvent(ReservationDetailsEvent.OnRetry) }
                )
                uiState.reservation != null -> {
                    val reservation = uiState.reservation!!
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        item { Spacer(modifier = Modifier.height(8.dp)) }

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
                                        ReservationHeaderInfo(reservation)
                                        ReservationBookingDetails(reservation)
                                    }
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(24.dp)
                                    ) {
                                        ReservationPaymentDetails(reservation)
                                        ReservationDetailsActions(reservation, viewModel)
                                    }
                                }
                            }
                        } else {
                            item { ReservationHeaderInfo(reservation) }
                            item { ReservationBookingDetails(reservation) }
                            item { ReservationPaymentDetails(reservation) }
                            item { ReservationDetailsActions(reservation, viewModel) }
                        }

                        item { Spacer(modifier = Modifier.height(24.dp)) }
                    }
                }
            }
        }
    }
}

@Composable
private fun ReservationHeaderInfo(reservation: Reservation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Guest Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(text = "Name", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = reservation.guestName, style = MaterialTheme.typography.bodyLarge)
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(text = "Phone", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = reservation.phoneNumber, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(text = "Email", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = reservation.email, style = MaterialTheme.typography.bodyLarge)
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(text = "Status", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(4.dp))
                        ReservationStatusChip(status = reservation.bookingStatus)
                    }
                }
            }
        }
    }
}

@Composable
private fun ReservationBookingDetails(reservation: Reservation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Booking Details",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(text = "Room Type", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = reservation.roomType, style = MaterialTheme.typography.bodyLarge)
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(text = "Room Number", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = reservation.roomNumber, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(text = "Check-in", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = reservation.checkInDate, style = MaterialTheme.typography.bodyLarge)
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(text = "Check-out", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = reservation.checkOutDate, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            Column {
                Text(text = "Special Requests", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = reservation.specialRequests, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
private fun ReservationPaymentDetails(reservation: Reservation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Payment Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Total Amount", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = "\$${reservation.totalAmount}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Payment Status", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                ReservationStatusChip(status = reservation.paymentStatus)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Booking Source", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = reservation.bookingSource, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun ReservationDetailsActions(reservation: Reservation, viewModel: ReservationDetailsViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Actions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Button(
                onClick = { viewModel.onEvent(ReservationDetailsEvent.OnEditClicked(reservation.id)) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Edit Reservation")
            }
            Button(
                onClick = { viewModel.onEvent(ReservationDetailsEvent.OnCancelReservationClicked(reservation.id)) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Cancel Reservation")
            }
        }
    }
}
