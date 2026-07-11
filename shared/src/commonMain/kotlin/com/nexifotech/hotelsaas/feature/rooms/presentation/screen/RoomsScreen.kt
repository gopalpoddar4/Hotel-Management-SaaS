package com.nexifotech.hotelsaas.feature.rooms.presentation.screen

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
import com.nexifotech.hotelsaas.feature.rooms.presentation.components.*
import com.nexifotech.hotelsaas.feature.rooms.presentation.event.RoomEvent
import com.nexifotech.hotelsaas.feature.rooms.presentation.viewmodel.RoomViewModel

@Composable
fun RoomsScreen(
    onNavigateToDetails: (String) -> Unit = {},
    viewModel: RoomViewModel = viewModel { RoomViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    if (uiState.isLoading && uiState.allRooms.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
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
                    text = "Rooms",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
                )
            }

            item {
                RoomSummaryCards(uiState.metrics, windowSizeClass)
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
                            RoomSearchAndFilter(
                                searchQuery = uiState.searchQuery,
                                onSearchQueryChange = { viewModel.onEvent(RoomEvent.OnSearchQueryChanged(it)) },
                                filters = uiState.filters,
                                selectedFilter = uiState.selectedFilter,
                                onFilterSelected = { viewModel.onEvent(RoomEvent.OnFilterSelected(it)) }
                            )

                            if (uiState.filteredRooms.isEmpty()) {
                                RoomEmptyState()
                            } else {
                                // Nested grids in LazyColumn are not allowed, so using a Row/Column based grid for Expanded/Medium
                                val columns = if (windowSizeClass == WindowSizeClass.Expanded) 2 else 1
                                val chunkedRooms = uiState.filteredRooms.chunked(columns)
                                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                    chunkedRooms.forEach { rowRooms ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                                        ) {
                                            rowRooms.forEach { room ->
                                                RoomCard(
                                                    room = room,
                                                    onClick = { onNavigateToDetails(room.id) },
                                                    modifier = Modifier.weight(1f)
                                                )
                                            }
                                            // Fill remaining space if last row is incomplete
                                            if (rowRooms.size < columns) {
                                                Spacer(modifier = Modifier.weight((columns - rowRooms.size).toFloat()))
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
                            RoomActionButtons(
                                onAddRoom = { /* Mock */ },
                                onAssignGuest = { /* Mock */ },
                                onMarkCleaning = { /* Mock */ },
                                onMarkMaintenance = { /* Mock */ }
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
                        RoomActionButtons(
                            onAddRoom = { /* Mock */ },
                            onAssignGuest = { /* Mock */ },
                            onMarkCleaning = { /* Mock */ },
                            onMarkMaintenance = { /* Mock */ }
                        )
                        RoomSearchAndFilter(
                            searchQuery = uiState.searchQuery,
                            onSearchQueryChange = { viewModel.onEvent(RoomEvent.OnSearchQueryChanged(it)) },
                            filters = uiState.filters,
                            selectedFilter = uiState.selectedFilter,
                            onFilterSelected = { viewModel.onEvent(RoomEvent.OnFilterSelected(it)) }
                        )
                        if (uiState.filteredRooms.isEmpty()) {
                            RoomEmptyState()
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                uiState.filteredRooms.forEach { room ->
                                    RoomCard(
                                        room = room,
                                        onClick = { onNavigateToDetails(room.id) }
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
