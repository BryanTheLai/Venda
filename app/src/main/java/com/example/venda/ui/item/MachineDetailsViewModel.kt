/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

