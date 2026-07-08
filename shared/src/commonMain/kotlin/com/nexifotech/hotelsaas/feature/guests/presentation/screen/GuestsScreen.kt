package com.nexifotech.hotelsaas.feature.guests.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.nexifotech.hotelsaas.feature.guests.presentation.components.*
import com.nexifotech.hotelsaas.feature.guests.presentation.event.GuestEvent
import com.nexifotech.hotelsaas.feature.guests.presentation.viewmodel.GuestViewModel

@Composable
fun GuestsScreen(
    onNavigateToDetails: (String) -> Unit = {},
    viewModel: GuestViewModel = viewModel { GuestViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    if (uiState.isLoading && uiState.allGuests.isEmpty()) {
        LoadingView()
    } else if (uiState.error != null && uiState.allGuests.isEmpty()) {
        ErrorView(
            message = uiState.error!!,
            onRetry = { viewModel.onEvent(GuestEvent.OnRefresh) }
        )
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
                    text = "Guests",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
                )
            }

            item {
                GuestSummaryCards(uiState.metrics, windowSizeClass)
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
                            GuestSearchAndFilter(
                                searchQuery = uiState.searchQuery,
                                onSearchQueryChange = { viewModel.onEvent(GuestEvent.OnSearchQueryChanged(it)) },
                                filters = uiState.filters,
                                selectedFilter = uiState.selectedFilter,
                                onFilterSelected = { viewModel.onEvent(GuestEvent.OnFilterSelected(it)) }
                            )

                            if (uiState.filteredGuests.isEmpty()) {
                                EmptyState(message = "No guests found.")
                            } else {
                                val columns = if (windowSizeClass == WindowSizeClass.Expanded) 2 else 1
                                val chunkedGuests = uiState.filteredGuests.chunked(columns)
                                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                    chunkedGuests.forEach { rowGuests ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                                        ) {
                                            rowGuests.forEach { guest ->
                                                GuestCard(
                                                    guest = guest,
                                                    onClick = { onNavigateToDetails(guest.id) },
                                                    modifier = Modifier.weight(1f)
                                                )
                                            }
                                            if (rowGuests.size < columns) {
                                                Spacer(modifier = Modifier.weight((columns - rowGuests.size).toFloat()))
                                            }
                                        }
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
                            GuestActionButtons(
                                onAddGuest = { /* Mock */ },
                                onCheckInGuest = { /* Mock */ },
                                onCheckOutGuest = { /* Mock */ }
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
                        GuestActionButtons(
                            onAddGuest = { /* Mock */ },
                            onCheckInGuest = { /* Mock */ },
                            onCheckOutGuest = { /* Mock */ }
                        )
                        GuestSearchAndFilter(
                            searchQuery = uiState.searchQuery,
                            onSearchQueryChange = { viewModel.onEvent(GuestEvent.OnSearchQueryChanged(it)) },
                            filters = uiState.filters,
                            selectedFilter = uiState.selectedFilter,
                            onFilterSelected = { viewModel.onEvent(GuestEvent.OnFilterSelected(it)) }
                        )
                        if (uiState.filteredGuests.isEmpty()) {
                            EmptyState(message = "No guests found.")
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                uiState.filteredGuests.forEach { guest ->
                                    GuestCard(
                                        guest = guest,
                                        onClick = { onNavigateToDetails(guest.id) }
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
