package com.example.venda.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.venda.data.MachinesRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve and update an machine from the [MachinesRepository]'s data source.
 */
class MachineEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val machinesRepository: MachinesRepository
) : ViewModel() {

    /**
     * Holds current machine ui state
     */
    var machineUiState by mutableStateOf(MachineUiState())
        private set

    private val machineId: Int = checkNotNull(savedStateHandle[MachineEditDestination.machineIdArg])

    private fun validateInput(uiState: MachineDetails = machineUiState.machineDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
                    && price.isNotBlank()
                    && capacity.isNotBlank()
                    && model.isNotBlank()
                    && dateInstalled.isNotBlank()
                    && location.isNotBlank()
                    && currentStatus.isNotBlank()
                    && serialNumber.isNotBlank()
        }
    }

    init {
        viewModelScope.launch {
            machineUiState = machinesRepository.getMachineStream(machineId)
                .filterNotNull()
                .first().toMachineUiState(true)
        }
    }

    fun updateUiState(machineDetails: MachineDetails) {
        machineUiState = MachineUiState(machineDetails = machineDetails, isEntryValid = validateInput(machineDetails))
    }

    suspend fun updateMachine() {
        if (validateInput(machineUiState.machineDetails)) {
            machinesRepository.updateMachine(machineUiState.machineDetails.toMachine())
        }
    }

}
