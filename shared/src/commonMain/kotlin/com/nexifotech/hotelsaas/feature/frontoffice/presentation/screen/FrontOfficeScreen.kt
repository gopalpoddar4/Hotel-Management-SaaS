package com.nexifotech.hotelsaas.feature.frontoffice.presentation.screen

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
import com.nexifotech.hotelsaas.feature.frontoffice.presentation.components.*
import com.nexifotech.hotelsaas.feature.frontoffice.presentation.event.FrontOfficeEvent
import com.nexifotech.hotelsaas.feature.frontoffice.presentation.viewmodel.FrontOfficeViewModel

@Composable
fun FrontOfficeScreen(
    viewModel: FrontOfficeViewModel = viewModel { FrontOfficeViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    if (uiState.isLoading && uiState.allGuests.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
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
                    text = "Front Office",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
                )
            }

            item {
                TodaySummarySection(uiState.metrics, windowSizeClass)
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
                                onSearchQueryChange = { viewModel.onEvent(FrontOfficeEvent.OnSearchQueryChanged(it)) },
                                filters = uiState.filters,
                                selectedFilter = uiState.selectedFilter,
                                onFilterSelected = { viewModel.onEvent(FrontOfficeEvent.OnFilterSelected(it)) }
                            )
                            GuestListSection(
                                guests = uiState.filteredGuests,
                                onGuestClicked = { viewModel.onEvent(FrontOfficeEvent.OnGuestClicked(it)) }
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            FrontOfficeQuickActions(
                                onEvent = viewModel::onEvent
                            )
                        }
                    }
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        FrontOfficeQuickActions(
                            onEvent = viewModel::onEvent
                        )
                        GuestSearchAndFilter(
                            searchQuery = uiState.searchQuery,
                            onSearchQueryChange = { viewModel.onEvent(FrontOfficeEvent.OnSearchQueryChanged(it)) },
                            filters = uiState.filters,
                            selectedFilter = uiState.selectedFilter,
                            onFilterSelected = { viewModel.onEvent(FrontOfficeEvent.OnFilterSelected(it)) }
                        )
                        GuestListSection(
                            guests = uiState.filteredGuests,
                            onGuestClicked = { viewModel.onEvent(FrontOfficeEvent.OnGuestClicked(it)) }
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
