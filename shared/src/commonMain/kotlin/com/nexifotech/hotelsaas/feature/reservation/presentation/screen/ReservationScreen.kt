package com.nexifotech.hotelsaas.feature.reservation.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.nexifotech.hotelsaas.feature.reservation.presentation.components.*
import com.nexifotech.hotelsaas.feature.reservation.presentation.event.ReservationEvent
import com.nexifotech.hotelsaas.feature.reservation.presentation.viewmodel.ReservationViewModel

@Composable
fun ReservationScreen(
    onNavigateToDetails: (String) -> Unit = {},
    viewModel: ReservationViewModel = viewModel { ReservationViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    if (uiState.isLoading && uiState.allReservations.isEmpty()) {
        LoadingView()
    } else {
        LazyColumn(
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
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    text = "Reservations",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
                )
            }

            item {
                ReservationSummaryCards(uiState.metrics, windowSizeClass)
            }

            item {
                if (windowSizeClass == WindowSizeClass.Expanded || windowSizeClass == WindowSizeClass.Medium) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(2f),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            ReservationSearchAndFilter(
                                searchQuery = uiState.searchQuery,
                                onSearchQueryChange = { viewModel.onEvent(ReservationEvent.OnSearchQueryChanged(it)) },
                                filters = uiState.filters,
                                selectedFilter = uiState.selectedFilter,
                                onFilterSelected = { viewModel.onEvent(ReservationEvent.OnFilterSelected(it)) }
                            )
                            
                            if (uiState.filteredReservations.isEmpty()) {
                                EmptyStateView()
                            } else {
                                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                    uiState.filteredReservations.forEach { res ->
                                        ReservationListCard(
                                            reservation = res,
                                            onClick = { onNavigateToDetails(res.id) }
                                        )
                                    }
                                }
                            }
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            Text(
                                text = "Quick Actions",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            ReservationActionButtons(
                                onNewReservation = { /* Mock */ },
                                onWalkIn = { /* Mock */ },
                                onExtendStay = { /* Mock */ },
                                onAssignRoom = { /* Mock */ },
                                onPrint = { /* Mock */ }
                            )
                        }
                    }
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Text(
                            text = "Quick Actions",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        ReservationActionButtons(
                            onNewReservation = { /* Mock */ },
                            onWalkIn = { /* Mock */ },
                            onExtendStay = { /* Mock */ },
                            onAssignRoom = { /* Mock */ },
                            onPrint = { /* Mock */ }
                        )
                        ReservationSearchAndFilter(
                            searchQuery = uiState.searchQuery,
                            onSearchQueryChange = { viewModel.onEvent(ReservationEvent.OnSearchQueryChanged(it)) },
                            filters = uiState.filters,
                            selectedFilter = uiState.selectedFilter,
                            onFilterSelected = { viewModel.onEvent(ReservationEvent.OnFilterSelected(it)) }
                        )
                        if (uiState.filteredReservations.isEmpty()) {
                            EmptyStateView()
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                uiState.filteredReservations.forEach { res ->
                                    ReservationListCard(
                                        reservation = res,
                                        onClick = { onNavigateToDetails(res.id) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
