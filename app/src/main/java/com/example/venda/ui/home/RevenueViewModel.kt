package com.example.venda.ui.revenue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.venda.data.Revenue
import com.example.venda.data.RevenuesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RevenueViewModel(revenuesRepository: RevenuesRepository): ViewModel() {

    val revenueUiState: StateFlow<RevenueUiState> =
        revenuesRepository.getAllRevenuesStream().map { RevenueUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = RevenueUiState()
            )


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for RevenueScreen
 */
data class RevenueUiState(val revenueList: List<Revenue> = listOf())