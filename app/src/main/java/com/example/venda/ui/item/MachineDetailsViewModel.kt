package com.example.venda.ui.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.venda.data.MachinesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve, update and delete an machine from the [MachinesRepository]'s data source.
 */
class MachineDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val machinesRepository: MachinesRepository
) : ViewModel() {

    private val machineId: Int = checkNotNull(savedStateHandle[MachineDetailsDestination.machineIdArg])

    val uiState: StateFlow<MachineDetailsUiState> =
        machinesRepository.getMachineStream(machineId)
            .filterNotNull()
            .map {
                MachineDetailsUiState(machineDetails = it.toMachineDetails())
                //MachineDetailsUiState(outOfStock = it.quantity <= 0, machineDetails = it.toMachineDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MachineDetailsUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

//    fun reduceQuantityByOne() {
//        viewModelScope.launch {
//            val currentMachine = uiState.value.machineDetails.toMachine()
//            if (currentMachine.quantity > 0) {
//                machinesRepository.updateMachine(currentMachine.copy(quantity = currentMachine.quantity - 1))
//            }
//        }
//    }

    suspend fun deleteMachine() {
        machinesRepository.deleteMachine(uiState.value.machineDetails.toMachine())
    }

}

/**
 * UI state for MachineDetailsScreen
 */
data class MachineDetailsUiState(
    //val outOfStock: Boolean = true,
    val machineDetails: MachineDetails = MachineDetails()
)

