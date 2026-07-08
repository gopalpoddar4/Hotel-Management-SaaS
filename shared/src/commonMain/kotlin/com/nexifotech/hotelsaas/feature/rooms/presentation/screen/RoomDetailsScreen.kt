package com.nexifotech.hotelsaas.feature.rooms.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.adaptive.rememberWindowSizeClass
import com.nexifotech.hotelsaas.feature.rooms.domain.model.Room
import com.nexifotech.hotelsaas.feature.rooms.domain.model.RoomStatus
import com.nexifotech.hotelsaas.feature.rooms.presentation.components.RoomStatusChip
import com.nexifotech.hotelsaas.feature.rooms.presentation.components.RoomAmenitiesCard
import com.nexifotech.hotelsaas.feature.rooms.presentation.components.RoomDetailsActionCard
import com.nexifotech.hotelsaas.feature.rooms.presentation.components.RoomGuestInfoCard
import com.nexifotech.hotelsaas.feature.rooms.presentation.components.RoomInfoCard
import com.nexifotech.hotelsaas.feature.rooms.presentation.event.RoomDetailsEvent
import com.nexifotech.hotelsaas.feature.rooms.presentation.viewmodel.RoomDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomDetailsScreen(
    roomId: String,
    onBack: () -> Unit,
    viewModel: RoomDetailsViewModel = viewModel { RoomDetailsViewModel(roomId = roomId) }
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Room Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading && uiState.room == null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text(text = uiState.error ?: "Unknown error", color = MaterialTheme.colorScheme.error)
            }
        } else {
            uiState.room?.let { room ->
                val contentModifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState)

                if (windowSizeClass == WindowSizeClass.Expanded || windowSizeClass == WindowSizeClass.Medium) {
                    Row(
                        modifier = contentModifier,
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(2f),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            RoomInfoCard(room = room)
                            RoomAmenitiesCard(room = room)
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            RoomGuestInfoCard(room = room)
                            RoomDetailsActionCard(
                                room = room,
                                onAssignGuest = { viewModel.onEvent(RoomDetailsEvent.OnAssignGuest("New Guest")) },
                                onReleaseRoom = { viewModel.onEvent(RoomDetailsEvent.OnReleaseRoom) },
                                onChangeStatus = { status -> viewModel.onEvent(RoomDetailsEvent.OnChangeStatus(status)) }
                            )
                        }
                    }
                } else {
                    Column(
                        modifier = contentModifier,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        RoomInfoCard(room = room)
                        RoomGuestInfoCard(room = room)
                        RoomAmenitiesCard(room = room)
                        RoomDetailsActionCard(
                            room = room,
                            onAssignGuest = { viewModel.onEvent(RoomDetailsEvent.OnAssignGuest("New Guest")) },
                            onReleaseRoom = { viewModel.onEvent(RoomDetailsEvent.OnReleaseRoom) },
                            onChangeStatus = { status -> viewModel.onEvent(RoomDetailsEvent.OnChangeStatus(status)) }
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

