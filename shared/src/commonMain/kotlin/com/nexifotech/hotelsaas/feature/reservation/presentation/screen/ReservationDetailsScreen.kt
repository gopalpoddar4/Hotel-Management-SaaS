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
                                        ReservationDetailsActions(
                                            reservation = reservation,
                                            onEditClicked = { viewModel.onEvent(ReservationDetailsEvent.OnEditClicked(it)) },
                                            onCancelClicked = { viewModel.onEvent(ReservationDetailsEvent.OnCancelReservationClicked(it)) }
                                        )
                                    }
                                }
                            }
                        } else {
                            item { ReservationHeaderInfo(reservation) }
                            item { ReservationBookingDetails(reservation) }
                            item { ReservationPaymentDetails(reservation) }
                            item { 
                                ReservationDetailsActions(
                                    reservation = reservation,
                                    onEditClicked = { viewModel.onEvent(ReservationDetailsEvent.OnEditClicked(it)) },
                                    onCancelClicked = { viewModel.onEvent(ReservationDetailsEvent.OnCancelReservationClicked(it)) }
                                ) 
                            }
                        }

                        item { Spacer(modifier = Modifier.height(24.dp)) }
                    }
                }
            }
        }
    }
}

