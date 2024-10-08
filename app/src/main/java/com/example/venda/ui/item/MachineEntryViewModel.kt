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
                    && capacity.isNotBlank()
                    && model.isNotBlank()
                    && year.isNotBlank()
                    && year != "0"
                    && month.isNotBlank()
                    && month != "0"
                    && day.isNotBlank()
                    && day != "0"
                    && location.isNotBlank()
                    && currentStatus.isNotBlank()
                    && serialNumber.isNotBlank()
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
    val capacity: String = "",
    val model: String  = "",
    val year: String  = "",
    val month: String  = "",
    val day: String  = "",
    val location: String  = "",
    val currentStatus: String  = "",
    val serialNumber: String  = "",
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
    capacity = capacity.toIntOrNull() ?: 0,
    model = model,
    year = year.toIntOrNull() ?: 0,
    month = month.toIntOrNull() ?: 0,
    day = day.toIntOrNull() ?: 0,
    location = location,
    currentStatus = currentStatus,
    serialNumber = serialNumber,

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
    capacity = capacity.toString(),
    model = model,
    year = year.toString(),
    month = month.toString(),
    day = day.toString(),
    location = location,
    currentStatus = currentStatus,
    serialNumber = serialNumber,

    )
