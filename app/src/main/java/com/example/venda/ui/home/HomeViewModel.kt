package com.example.venda.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.venda.data.Machine
import com.example.venda.data.MachinesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


/**
 * ViewModel to retrieve all machines in the Room database.
 */
class HomeViewModel(machinesRepository: MachinesRepository): ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        machinesRepository.getAllMachinesStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val machineList: List<Machine> = listOf())
