package com.example.venda.ui.dashboard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.venda.data.MachineStatusCount
import com.example.venda.data.MachinesRepository
import com.example.venda.data.RevenuesRepository
import com.example.venda.data.YearMonthRevenue
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar

class DashboardViewModel(savedStateHandle: SavedStateHandle, machinesRepository: MachinesRepository, revenuesRepository: RevenuesRepository): ViewModel() {

    // Get current date and time
    val calendar = Calendar.getInstance()

    // Extract year and month (0-based indexing for month)
    val currentYear: Int = calendar.get(Calendar.YEAR)
    val currentMonth: Int = 5//calendar.get(Calendar.MONTH) + 1 // Adjust for 0-based indexing


    val dashboardCurrentYearRevenueUiState: StateFlow<CurrentYearRevenueUiState> =
        revenuesRepository.getCurrentYearTotalRevenueStream(currentYear)
            .map { CurrentYearRevenueUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CurrentYearRevenueUiState()
            )

    val dashboardCurrentMonthRevenueUiState: StateFlow<CurrentMonthRevenueUiState> =
        revenuesRepository.getCurrentMonthTotalRevenueStream(currentYear = currentYear, currentMonth = currentMonth)
            .map { CurrentMonthRevenueUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CurrentMonthRevenueUiState()
            )

    val dashboardMachineStatusUiState: StateFlow<MachineStatusCountUiState> =
        machinesRepository.getMachineStatusCountsStream()
            .map { MachineStatusCountUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MachineStatusCountUiState()
            )

    val dashboardYearChartRevenueUiState: StateFlow<YearRevenueUiState> =
        revenuesRepository.getYearMonthRevenueStream(currentYear = currentYear)
            .map { YearRevenueUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = YearRevenueUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
data class CurrentYearRevenueUiState(
    val currentYearRevenue: Double? = 0.0 // Allow null for initial state
)
data class CurrentMonthRevenueUiState(
    val currentMonthRevenue: Double? = 0.0 // Allow null for initial state
)

data class MachineStatusCountUiState(val machineStatusCount: List<MachineStatusCount> = listOf())

data class YearRevenueUiState(val yearRevenueData: List<YearMonthRevenue> = listOf())

