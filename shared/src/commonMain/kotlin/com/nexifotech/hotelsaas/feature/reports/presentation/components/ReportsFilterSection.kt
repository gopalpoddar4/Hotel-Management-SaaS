package com.nexifotech.hotelsaas.feature.reports.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsFilterSection(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    selectedDateFilter: String,
    onDateFilterSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("All", "Revenue", "Occupancy", "Booking", "Guest", "Housekeeping", "Restaurant", "Financial")
    val dateFilters = listOf("All Time", "Daily", "Weekly", "Monthly", "Yearly")

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                FilterChip(
                    selected = category == selectedCategory,
                    onClick = { onCategorySelected(category) },
                    label = { Text(category) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            dateFilters.forEach { filter ->
                FilterChip(
                    selected = filter == selectedDateFilter,
                    onClick = { onDateFilterSelected(filter) },
                    label = { Text(filter) }
                )
            }
        }
    }
}
