package com.nexifotech.hotelsaas.feature.restaurant.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Receipt
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
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.OrderStatus
import com.nexifotech.hotelsaas.feature.restaurant.presentation.components.OrderStatusChip
import com.nexifotech.hotelsaas.feature.restaurant.presentation.components.PaymentStatusChip
import com.nexifotech.hotelsaas.feature.restaurant.presentation.components.RestaurantErrorState
import com.nexifotech.hotelsaas.feature.restaurant.presentation.components.RestaurantLoadingView
import com.nexifotech.hotelsaas.feature.restaurant.presentation.event.RestaurantDetailsEvent
import com.nexifotech.hotelsaas.feature.restaurant.presentation.viewmodel.RestaurantDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantOrderDetailsScreen(
    orderId: String,
    onBackClick: () -> Unit,
    viewModel: RestaurantDetailsViewModel = viewModel { RestaurantDetailsViewModel(orderId) }
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
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
            if (uiState.isLoading && uiState.order == null) {
                RestaurantLoadingView()
            } else if (uiState.error != null && uiState.order == null) {
                RestaurantErrorState(error = uiState.error!!)
            } else if (uiState.order != null) {
                val order = uiState.order!!
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        if (windowSizeClass == WindowSizeClass.Expanded || windowSizeClass == WindowSizeClass.Medium) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(24.dp)
                            ) {
                                Column(modifier = Modifier.weight(2f), verticalArrangement = Arrangement.spacedBy(24.dp)) {
                                    OrderInfoSection(order)
                                    OrderItemsSection(order)
                                }
                                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(24.dp)) {
                                    OrderSummarySection(order)
                                    OrderActionsSection(order, viewModel)
                                }
                            }
                        } else {
                            OrderInfoSection(order)
                            OrderItemsSection(order)
                            OrderSummarySection(order)
                            OrderActionsSection(order, viewModel)
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderInfoSection(order: com.nexifotech.hotelsaas.feature.restaurant.domain.model.Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Order Information", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            HorizontalDivider()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Order ID", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(order.orderNumber, fontWeight = FontWeight.SemiBold)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Guest Name", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(order.guestName)
            }
            if (order.tableNumber != null) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Table Number", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(order.tableNumber)
                }
            }
            if (order.roomNumber != null) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Room Number", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(order.roomNumber)
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Order Type", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(order.orderType.name.replace("_", " "))
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Time", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(order.orderTime)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Status", color = MaterialTheme.colorScheme.onSurfaceVariant)
                OrderStatusChip(order.status)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Payment", color = MaterialTheme.colorScheme.onSurfaceVariant)
                PaymentStatusChip(order.paymentStatus)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Staff", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(order.assignedStaff)
            }
            if (order.orderNotes.isNotBlank()) {
                HorizontalDivider()
                Text("Notes", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(order.orderNotes, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun OrderItemsSection(order: com.nexifotech.hotelsaas.feature.restaurant.domain.model.Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Ordered Items", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            HorizontalDivider()
            order.items.forEach { item ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.menuItem.name, fontWeight = FontWeight.SemiBold)
                        Text("${item.quantity}x @ $${item.menuItem.price}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text("$${item.price}", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun OrderSummarySection(order: com.nexifotech.hotelsaas.feature.restaurant.domain.model.Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Billing Summary", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            HorizontalDivider()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Subtotal", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("$${order.subtotal}")
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Taxes", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("$${order.taxes}")
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Discount", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("-$${order.discount}")
            }
            HorizontalDivider()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Grand Total", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("$${order.grandTotal}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
private fun OrderActionsSection(
    order: com.nexifotech.hotelsaas.feature.restaurant.domain.model.Order,
    viewModel: RestaurantDetailsViewModel
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Actions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            HorizontalDivider()

            when (order.status) {
                OrderStatus.PREPARING -> {
                    Button(
                        onClick = { viewModel.onEvent(RestaurantDetailsEvent.UpdateOrderStatus(OrderStatus.READY)) },
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Mark as Ready")
                    }
                }
                OrderStatus.READY -> {
                    Button(
                        onClick = { viewModel.onEvent(RestaurantDetailsEvent.UpdateOrderStatus(OrderStatus.SERVED)) },
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Mark as Served")
                    }
                }
                OrderStatus.SERVED -> {
                    Button(
                        onClick = { viewModel.onEvent(RestaurantDetailsEvent.UpdateOrderStatus(OrderStatus.COMPLETED)) },
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Mark as Completed")
                    }
                }
                else -> {}
            }

            if (order.status != OrderStatus.CANCELLED && order.status != OrderStatus.COMPLETED) {
                FilledTonalButton(
                    onClick = { viewModel.onEvent(RestaurantDetailsEvent.GenerateBill) },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Icon(Icons.Default.Receipt, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                    Text("Generate Bill")
                }
            }

            if (order.status == OrderStatus.PREPARING || order.status == OrderStatus.READY) {
                OutlinedButton(
                    onClick = { viewModel.onEvent(RestaurantDetailsEvent.CancelOrder) },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Cancel Order")
                }
            }
        }
    }
}
