package com.nexifotech.hotelsaas.feature.housekeeping.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.components.HousekeepingFilterSection
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.components.HousekeepingSearchBar
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.components.HousekeepingSummaryCard
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.components.TaskCard
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.event.HousekeepingEvent
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.viewmodel.HousekeepingViewModel

@Composable
fun HousekeepingScreen(
    viewModel: HousekeepingViewModel = viewModel { HousekeepingViewModel() },
    onNavigateToDetails: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = uiState.error!!, color = MaterialTheme.colorScheme.error)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    Text(
                        text = "House Keeping",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
                    )
                }
                item {
                    uiState.summary?.let { summary ->
                        HousekeepingSummaryCard(summary = summary)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                item {
                    HousekeepingSearchBar(
                        query = uiState.searchQuery,
                        onQueryChange = { viewModel.onEvent(HousekeepingEvent.OnSearchQueryChanged(it)) }
                    )
                }

                item {
                    HousekeepingFilterSection(
                        filters = uiState.filters,
                        selectedFilter = uiState.selectedFilter,
                        onFilterSelected = { viewModel.onEvent(HousekeepingEvent.OnFilterSelected(it)) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(uiState.filteredTasks, key = { it.id }) { task ->
                    TaskCard(
                        task = task,
                        onClick = {
                            viewModel.onEvent(HousekeepingEvent.OnTaskClicked(task.id))
                            onNavigateToDetails(task.id)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (uiState.filteredTasks.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxSize().height(200.dp), contentAlignment = Alignment.Center) {
                            Text(
                                text = "No tasks found",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
