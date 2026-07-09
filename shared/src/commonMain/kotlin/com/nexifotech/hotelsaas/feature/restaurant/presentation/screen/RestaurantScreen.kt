package com.nexifotech.hotelsaas.feature.restaurant.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.RoomService
import androidx.compose.material.icons.filled.TableBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.adaptive.rememberWindowSizeClass
import com.nexifotech.hotelsaas.feature.restaurant.presentation.components.*
import com.nexifotech.hotelsaas.feature.restaurant.presentation.event.RestaurantEvent
import com.nexifotech.hotelsaas.feature.restaurant.presentation.viewmodel.RestaurantViewModel

@Composable
fun RestaurantScreen(
    onNavigateToOrderDetails: (String) -> Unit = {},
    viewModel: RestaurantViewModel = viewModel { RestaurantViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    if (uiState.isLoading && uiState.summary.activeOrders == 0) {
        RestaurantLoadingView()
    } else if (uiState.error != null && uiState.summary.activeOrders == 0) {
        RestaurantErrorState(error = uiState.error!!)
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
                    text = "Restaurant",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
                )
            }

            item {
                RestaurantSummaryCards(uiState.summary, windowSizeClass)
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
                            RestaurantSearchAndFilter(
                                searchQuery = uiState.searchQuery,
                                onSearchQueryChange = { viewModel.onEvent(RestaurantEvent.OnSearchQueryChanged(it)) },
                                filters = uiState.orderFilters,
                                selectedFilter = uiState.selectedOrderFilter,
                                onFilterSelected = { viewModel.onEvent(RestaurantEvent.OnFilterSelected(it)) }
                            )

                            Text(
                                text = "Active Orders",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            if (uiState.filteredOrders.isEmpty()) {
                                RestaurantEmptyState("No orders found")
                            } else {
                                val columns = if (windowSizeClass == WindowSizeClass.Expanded) 2 else 1
                                val chunkedOrders = uiState.filteredOrders.chunked(columns)
                                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                    chunkedOrders.forEach { rowOrders ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                                        ) {
                                            rowOrders.forEach { order ->
                                                OrderCard(
                                                    order = order,
                                                    onClick = { onNavigateToOrderDetails(order.id) },
                                                    modifier = Modifier.weight(1f)
                                                )
                                            }
                                            if (rowOrders.size < columns) {
                                                Spacer(modifier = Modifier.weight((columns - rowOrders.size).toFloat()))
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
                            RestaurantActionButtons(
                                onNewOrder = { /* Mock */ },
                                onRoomService = { /* Mock */ },
                                onTableBooking = { /* Mock */ },
                                onGenerateBill = { /* Mock */ }
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Tables Status",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                uiState.tables.take(4).forEach { table ->
                                    TableCard(table = table)
                                }
                            }
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
                        RestaurantActionButtons(
                            onNewOrder = { /* Mock */ },
                            onRoomService = { /* Mock */ },
                            onTableBooking = { /* Mock */ },
                            onGenerateBill = { /* Mock */ }
                        )
                        
                        RestaurantSearchAndFilter(
                            searchQuery = uiState.searchQuery,
                            onSearchQueryChange = { viewModel.onEvent(RestaurantEvent.OnSearchQueryChanged(it)) },
                            filters = uiState.orderFilters,
                            selectedFilter = uiState.selectedOrderFilter,
                            onFilterSelected = { viewModel.onEvent(RestaurantEvent.OnFilterSelected(it)) }
                        )

                        Text(
                            text = "Active Orders",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        if (uiState.filteredOrders.isEmpty()) {
                            RestaurantEmptyState("No orders found")
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                uiState.filteredOrders.forEach { order ->
                                    OrderCard(
                                        order = order,
                                        onClick = { onNavigateToOrderDetails(order.id) }
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tables Status",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(uiState.tables) { table ->
                                TableCard(table = table, modifier = Modifier.width(260.dp))
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

@Composable
private fun RestaurantActionButtons(
    onNewOrder: () -> Unit,
    onRoomService: () -> Unit,
    onTableBooking: () -> Unit,
    onGenerateBill: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = onNewOrder,
                modifier = Modifier.weight(1f).height(56.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                Text("New Order")
            }
            FilledTonalButton(
                onClick = onRoomService,
                modifier = Modifier.weight(1f).height(56.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.RoomService, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                Text("Room Service")
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = onTableBooking,
                modifier = Modifier.weight(1f).height(56.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.TableBar, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                Text("Table Booking")
            }
            OutlinedButton(
                onClick = onGenerateBill,
                modifier = Modifier.weight(1f).height(56.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Receipt, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                Text("Generate Bill")
            }
        }
    }
}