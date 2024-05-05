package com.example.venda.ui.csv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.venda.data.Machine
import com.example.venda.data.MachinesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CsvViewModel(machinesRepository: MachinesRepository): ViewModel() {

    val csvUiState: StateFlow<CsvUiState> =
        machinesRepository.getAllMachinesStream().map { CsvUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CsvUiState()
            )


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for CsvScreen
 */
data class CsvUiState(val machineList: List<Machine> = listOf())
