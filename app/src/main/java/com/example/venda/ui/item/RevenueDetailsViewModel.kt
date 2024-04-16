package com.example.venda.ui.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.venda.data.RevenuesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve, update and delete an revenue from the [RevenuesRepository]'s data source.
 */
class RevenueDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val revenuesRepository: RevenuesRepository
) : ViewModel() {

    private val revenueId: Int = checkNotNull(savedStateHandle[RevenueDetailsDestination.revenueIdArg])

    val uiState: StateFlow<RevenueDetailsUiState> =
        revenuesRepository.getRevenueStream(revenueId)
            .filterNotNull()
            .map {
                RevenueDetailsUiState(revenueDetails = it.toRevenueDetails())
                //RevenueDetailsUiState(outOfStock = it.quantity <= 0, revenueDetails = it.toRevenueDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = RevenueDetailsUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

//    fun reduceQuantityByOne() {
//        viewModelScope.launch {
//            val currentRevenue = uiState.value.revenueDetails.toRevenue()
//            if (currentRevenue.quantity > 0) {
//                revenuesRepository.updateRevenue(currentRevenue.copy(quantity = currentRevenue.quantity - 1))
//            }
//        }
//    }

    suspend fun deleteRevenue() {
        revenuesRepository.deleteRevenue(uiState.value.revenueDetails.toRevenue())
    }

}

/**
 * UI state for RevenueDetailsScreen
 */
data class RevenueDetailsUiState(
    //val outOfStock: Boolean = true,
    val revenueDetails: RevenueDetails = RevenueDetails()
)

