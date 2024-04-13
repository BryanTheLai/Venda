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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.venda.data.Machine
import com.example.venda.data.MachinesRepository
import java.text.NumberFormat

/**
 * ViewModel to validate and insert machines in the Room database.
 */
class MachineEntryViewModel(private val machinesRepository: MachinesRepository) : ViewModel() {

    /**
     * Holds current machine ui state
     */
    var machineUiState by mutableStateOf(MachineUiState())
        private set

    /**
     * Updates the [machineUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(machineDetails: MachineDetails) {
        machineUiState =
            MachineUiState(machineDetails = machineDetails, isEntryValid = validateInput(machineDetails))
    }

    private fun validateInput(uiState: MachineDetails = machineUiState.machineDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
                    && price.isNotBlank()
                    && model.isNotBlank()
                    && dateInstalled.isNotBlank()
                    && location.isNotBlank()
            //&& quantity.isNotBlank()
        }
    }

    suspend fun saveMachine() {
        if (validateInput()) {
            machinesRepository.insertMachine(machineUiState.machineDetails.toMachine())
        }

    }
}

/**
 * Represents Ui State for an Machine.
 */
data class MachineUiState(
    val machineDetails: MachineDetails = MachineDetails(),
    val isEntryValid: Boolean = false
)

data class MachineDetails(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
    val model: String  = "",
    val dateInstalled: String  = "",
    val location: String  = "",
)

/**
 * Extension function to convert [MachineDetails] to [Machine]. If the value of [MachineDetails.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [MachineDetails.quantity] is not a valid [Int], then the quantity will be set to 0
 */
fun MachineDetails.toMachine(): Machine = Machine(
    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0,
    model = model,
    dateInstalled = dateInstalled.toLongOrNull() ?: 0L,
    location = location,

)

fun Machine.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

/**
 * Extension function to convert [Machine] to [MachineUiState]
 */
fun Machine.toMachineUiState(isEntryValid: Boolean = false): MachineUiState = MachineUiState(
    machineDetails = this.toMachineDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Machine] to [MachineDetails]
 */
fun Machine.toMachineDetails(): MachineDetails = MachineDetails(
    id = id,
    name = name,
    price = price.toString(),
    quantity = quantity.toString(),
    model = model,
    dateInstalled = dateInstalled.toString(),
    location = location,
)
