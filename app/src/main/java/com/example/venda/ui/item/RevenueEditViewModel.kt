package com.example.venda.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.venda.data.RevenuesRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve and update an revenue from the [RevenuesRepository]'s data source.
 */
class RevenueEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val revenuesRepository: RevenuesRepository
) : ViewModel() {

    /**
     * Holds current revenue ui state
     */
    var revenueUiState by mutableStateOf(RevenueUiState())
        private set

    private val revenueId: Int = checkNotNull(savedStateHandle[RevenueEditDestination.revenueIdArg])

    private fun validateInput(uiState: RevenueDetails = revenueUiState.revenueDetails): Boolean {
        return with(uiState) {
            year.isNotBlank() && month.isNotBlank() && revenue.isNotBlank()
                    && month != ""  && month.toInt() < 13 && month.toInt() > 0
        }
    }

    init {
        viewModelScope.launch {
            revenueUiState = revenuesRepository.getRevenueStream(revenueId)
                .filterNotNull()
                .first().toRevenueUiState(true)
        }
    }

    fun updateUiState(revenueDetails: RevenueDetails) {
        revenueUiState = RevenueUiState(revenueDetails = revenueDetails, isEntryValid = validateInput(revenueDetails))
    }

    suspend fun updateRevenue() {
        if (validateInput(revenueUiState.revenueDetails)) {
            revenuesRepository.updateRevenue(revenueUiState.revenueDetails.toRevenue())
        }
    }

}
