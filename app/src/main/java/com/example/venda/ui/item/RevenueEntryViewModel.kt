package com.example.venda.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.venda.data.Revenue
import com.example.venda.data.RevenuesRepository
import java.text.NumberFormat

/**
 * ViewModel to validate and insert revenues in the Room database.
 */
class RevenueEntryViewModel(savedStateHandle: SavedStateHandle, private val revenuesRepository: RevenuesRepository) : ViewModel() {
    val machineDetails: Int = checkNotNull(savedStateHandle[MachineDetailsDestination.machineIdArg])

    /**
     * Holds current revenue ui state
     */
    var revenueUiState by mutableStateOf(
        RevenueUiState(
            revenueDetails = RevenueDetails(machineId = machineDetails.toString()),
            isEntryValid = false
    ))
        private set

    /**
     * Updates the [revenueUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(revenueDetails: RevenueDetails) {
        revenueUiState =
            RevenueUiState(revenueDetails = revenueDetails, isEntryValid = validateInput(revenueDetails))
    }

    private fun validateInput(uiState: RevenueDetails = revenueUiState.revenueDetails): Boolean {
        return with(uiState) {
            year.isNotBlank() && month.isNotBlank() && revenue.isNotBlank()
                    && month != ""  && month.toInt() < 13 && month.toInt() > 0
        }
    }


    suspend fun saveRevenue() {
        if (validateInput()) {
            revenuesRepository.insertRevenue(revenueUiState.revenueDetails.toRevenue())
        }

    }
}

/**
 * Represents Ui State for an Revenue.
 */
data class RevenueUiState(
    val revenueDetails: RevenueDetails = RevenueDetails(),
    val isEntryValid: Boolean = false
)

data class RevenueDetails(
    val id: Int = 0,
    val machineId: String = "",
    val revenue: String = "",
    val year: String = "",
    val month: String  = "",
)

/**
 * Extension function to convert [RevenueDetails] to [Revenue]. If the value of [RevenueDetails.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [RevenueDetails.quantity] is not a valid [Int], then the quantity will be set to 0
 */
fun RevenueDetails.toRevenue(): Revenue = Revenue(
    id = id,
    machineId = machineId.toIntOrNull() ?: 0,
    revenue = revenue.toDoubleOrNull() ?: 0.0,
    year = year.toIntOrNull() ?: 0,
    month = month.toIntOrNull() ?: 0,
)

fun Revenue.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(revenue)
}

/**
 * Extension function to convert [Revenue] to [RevenueUiState]
 */
fun Revenue.toRevenueUiState(isEntryValid: Boolean = false): RevenueUiState = RevenueUiState(
    revenueDetails = this.toRevenueDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Revenue] to [RevenueDetails]
 */
fun Revenue.toRevenueDetails(): RevenueDetails = RevenueDetails(
    id = id,
    machineId = machineId.toString(),
    revenue = revenue.toString(),
    year = year.toString(),
    month = month.toString(),
    )
