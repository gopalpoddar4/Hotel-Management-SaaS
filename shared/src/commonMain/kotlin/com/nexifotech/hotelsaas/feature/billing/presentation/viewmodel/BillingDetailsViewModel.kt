package com.nexifotech.hotelsaas.feature.billing.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.billing.data.datasource.FakeBillingDataSource
import com.nexifotech.hotelsaas.feature.billing.data.repository.BillingRepositoryImpl
import com.nexifotech.hotelsaas.feature.billing.domain.usecase.GetInvoiceByIdUseCase
import com.nexifotech.hotelsaas.feature.billing.domain.usecase.MarkInvoicePaidUseCase
import com.nexifotech.hotelsaas.feature.billing.presentation.event.BillingDetailsEvent
import com.nexifotech.hotelsaas.feature.billing.presentation.state.BillingDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BillingDetailsViewModel : ViewModel() {
    
    // Using a shared dummy instance would be better, but we'll instantiate for now
    // In a real app this is injected via DI.
    private val dataSource = FakeBillingDataSource()
    private val repository = BillingRepositoryImpl(dataSource)
    
    private val getInvoiceByIdUseCase = GetInvoiceByIdUseCase(repository)
    private val markInvoicePaidUseCase = MarkInvoicePaidUseCase(repository)

    private val _uiState = MutableStateFlow(BillingDetailsUiState())
    val uiState: StateFlow<BillingDetailsUiState> = _uiState.asStateFlow()
    
    private var currentInvoiceId: String? = null

    fun onEvent(event: BillingDetailsEvent) {
        when (event) {
            is BillingDetailsEvent.LoadInvoice -> {
                currentInvoiceId = event.id
                loadInvoice(event.id)
            }
            is BillingDetailsEvent.MarkAsPaid -> markAsPaid()
            is BillingDetailsEvent.PrintInvoice -> {
                // Dummy print action
            }
            is BillingDetailsEvent.SendInvoice -> {
                // Dummy send action
            }
        }
    }

    private fun loadInvoice(id: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                // Simulate network delay
                val invoice = getInvoiceByIdUseCase(id)
                if (invoice != null) {
                    _uiState.update { it.copy(isLoading = false, invoice = invoice) }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "Invoice not found") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Failed to load invoice") }
            }
        }
    }

    private fun markAsPaid() {
        val id = currentInvoiceId ?: return
        viewModelScope.launch {
            try {
                markInvoicePaidUseCase(id)
                // Reload to get updated state
                loadInvoice(id)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Failed to mark as paid") }
            }
        }
    }
}
