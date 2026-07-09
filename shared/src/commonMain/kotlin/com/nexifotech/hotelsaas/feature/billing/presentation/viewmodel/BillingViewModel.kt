package com.nexifotech.hotelsaas.feature.billing.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.billing.data.datasource.FakeBillingDataSource
import com.nexifotech.hotelsaas.feature.billing.data.repository.BillingRepositoryImpl
import com.nexifotech.hotelsaas.feature.billing.domain.usecase.FilterInvoicesUseCase
import com.nexifotech.hotelsaas.feature.billing.domain.usecase.GetBillingSummaryUseCase
import com.nexifotech.hotelsaas.feature.billing.domain.usecase.GetInvoicesUseCase
import com.nexifotech.hotelsaas.feature.billing.domain.usecase.SearchInvoicesUseCase
import com.nexifotech.hotelsaas.feature.billing.presentation.event.BillingEvent
import com.nexifotech.hotelsaas.feature.billing.presentation.state.BillingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// For KMP without real DI in this scoped feature, we instantiate the dependencies directly or pass them.
// To keep it simple and backend-ready, we'll initialize them here (in real app they'd be injected).
class BillingViewModel : ViewModel() {

    private val dataSource = FakeBillingDataSource()
    private val repository = BillingRepositoryImpl(dataSource)

    private val getInvoicesUseCase = GetInvoicesUseCase(repository)
    private val searchInvoicesUseCase = SearchInvoicesUseCase(repository)
    private val filterInvoicesUseCase = FilterInvoicesUseCase(repository)
    private val getBillingSummaryUseCase = GetBillingSummaryUseCase(repository)

    private val _uiState = MutableStateFlow(BillingUiState())
    val uiState: StateFlow<BillingUiState> = _uiState.asStateFlow()

    init {
        loadData()
        
        // React to data source changes
        viewModelScope.launch {
            getInvoicesUseCase().collect { invoices ->
                _uiState.update { it.copy(allInvoices = invoices) }
                applyFilters()
                updateSummary()
            }
        }
    }

    fun onEvent(event: BillingEvent) {
        when (event) {
            is BillingEvent.LoadInvoices -> loadData()
            is BillingEvent.OnFilterSelected -> {
                _uiState.update { it.copy(selectedFilter = event.filter) }
                applyFilters()
            }
            is BillingEvent.OnSearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = event.query) }
                applyFilters()
            }
            is BillingEvent.OnInvoiceClicked -> {
                // Handle navigation in UI
            }
        }
    }

    private fun loadData() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                // Invoices flow is collected in init block
                updateSummary()
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "An error occurred") }
            }
        }
    }

    private fun applyFilters() {
        viewModelScope.launch {
            val query = _uiState.value.searchQuery
            val filter = _uiState.value.selectedFilter
            
            // In a real app we'd combine flow or call use cases sequentially
            val searchResults = searchInvoicesUseCase(query)
            val finalResults = if (filter != null) {
                searchResults.filter { it.paymentStatus == filter }
            } else {
                searchResults
            }
            
            _uiState.update { it.copy(filteredInvoices = finalResults) }
        }
    }

    private fun updateSummary() {
        viewModelScope.launch {
            val summary = getBillingSummaryUseCase()
            _uiState.update { it.copy(summary = summary) }
        }
    }
}
