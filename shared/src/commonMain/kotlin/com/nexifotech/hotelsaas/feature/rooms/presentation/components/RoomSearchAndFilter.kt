package com.nexifotech.hotelsaas.feature.rooms.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.feature.rooms.domain.model.RoomStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomSearchAndFilter(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    filters: List<RoomStatus>,
    selectedFilter: RoomStatus?,
    onFilterSelected: (RoomStatus?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search by Room Number or Type...") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            singleLine = true
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedFilter == null,
                    onClick = { onFilterSelected(null) },
                    label = { Text("All Rooms") },
                    shape = RoundedCornerShape(16.dp)
                )
            }
            items(filters) { filter ->
                val statusText = filter.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = {
                        if (selectedFilter == filter) {
                            onFilterSelected(null)
                        } else {
                            onFilterSelected(filter)
                        }
                    },
                    label = { Text(statusText) },
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }
    }
}
