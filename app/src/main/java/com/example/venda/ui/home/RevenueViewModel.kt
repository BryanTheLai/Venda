package com.example.venda.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.venda.data.Revenue
import com.example.venda.data.RevenuesRepository
import com.example.venda.ui.item.MachineDetailsDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RevenueViewModel(savedStateHandle: SavedStateHandle,
                       revenuesRepository: RevenuesRepository): ViewModel() {
    private val machineId: Int = checkNotNull(savedStateHandle[MachineDetailsDestination.machineIdArg])


    val revenueUiState: StateFlow<RevenueUiState> =
        revenuesRepository.getAllRevenuesForMachine(machineId).map { RevenueUiState(it) }
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