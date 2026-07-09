package com.nexifotech.hotelsaas.feature.reports.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.adaptive.rememberWindowSizeClass
import com.nexifotech.hotelsaas.feature.reports.domain.model.ReportDetailsData
import com.nexifotech.hotelsaas.feature.reports.presentation.components.AnalyticsPlaceholderCard
import com.nexifotech.hotelsaas.feature.reports.presentation.components.EmptyState
import com.nexifotech.hotelsaas.feature.reports.presentation.components.ErrorState
import com.nexifotech.hotelsaas.feature.reports.presentation.components.InsightCard
import com.nexifotech.hotelsaas.feature.reports.presentation.components.LoadingView
import com.nexifotech.hotelsaas.feature.reports.presentation.components.QuickActionSection
import com.nexifotech.hotelsaas.feature.reports.presentation.components.ReportStatisticsGrid
import com.nexifotech.hotelsaas.feature.reports.presentation.components.StatusChip
import com.nexifotech.hotelsaas.feature.reports.presentation.event.ReportDetailsEvent
import com.nexifotech.hotelsaas.feature.reports.presentation.state.ReportDetailsUiState
import com.nexifotech.hotelsaas.feature.reports.presentation.viewmodel.ReportDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportDetailsScreen(
    reportId: String,
    onBackClick: () -> Unit,
    viewModel: ReportDetailsViewModel = viewModel { ReportDetailsViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    LaunchedEffect(reportId) {
        viewModel.onEvent(ReportDetailsEvent.LoadReport(reportId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.reportDetails?.report?.name ?: "Report Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)
                        )
                    )
                )
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                LoadingView()
            } else if (uiState.error != null) {
                ErrorState(message = uiState.error ?: "Unknown error")
            } else if (uiState.reportDetails == null) {
                EmptyState(message = "Report not found")
            } else {
                ReportDetailsContent(
                    data = uiState.reportDetails!!,
                    windowSizeClass = windowSizeClass,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}

@Composable
private fun ReportDetailsContent(
    data: ReportDetailsData,
    windowSizeClass: WindowSizeClass,
    onEvent: (ReportDetailsEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            ReportHeaderSection(data)
        }

        item {
            QuickActionSection(
                onExportPdf = { onEvent(ReportDetailsEvent.ExportPdf) },
                onExportExcel = { onEvent(ReportDetailsEvent.ExportExcel) },
                onShare = { onEvent(ReportDetailsEvent.Share) }
            )
        }

        if (windowSizeClass == WindowSizeClass.Expanded) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(modifier = Modifier.weight(2f)) {
                        Text(
                            text = "Key Performance Indicators",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        ReportStatisticsGrid(kpis = data.kpis)
                        Spacer(modifier = Modifier.height(24.dp))
                        AnalyticsPlaceholderCard(title = "Trend Analysis")
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Insights & Recommendations",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        data.insights.forEach { insight ->
                            InsightCard(insight = insight, modifier = Modifier.padding(bottom = 12.dp))
                        }
                    }
                }
            }
        } else {
            item {
                Text(
                    text = "Key Performance Indicators",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                ReportStatisticsGrid(kpis = data.kpis)
            }
            
            item {
                AnalyticsPlaceholderCard(title = "Trend Analysis")
            }

            item {
                Text(
                    text = "Insights & Recommendations",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            
            items(data.insights) { insight ->
                InsightCard(insight = insight, modifier = Modifier.padding(bottom = 12.dp))
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun ReportHeaderSection(data: ReportDetailsData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = data.report.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Category: ${data.report.category}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = " • ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = data.report.dateRange,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            StatusChip(status = data.report.status)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Generated By",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = data.report.generatedBy,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
            Column {
                Text(
                    text = "Generated Time",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = data.report.generatedTime,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
            Column {
                Text(
                    text = "Last Updated",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = data.report.lastUpdated,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
