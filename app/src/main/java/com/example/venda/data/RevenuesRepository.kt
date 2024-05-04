package com.example.venda.data

import kotlinx.coroutines.flow.Flow

interface RevenuesRepository {
    fun getRevenuesForRevenueStream(revenueId: Int): Flow<List<Revenue>>
    fun getAllRevenuesForMachine(machineId: Int): Flow<List<Revenue>>

    fun getAllRevenuesStream(): Flow<List<Revenue>>

    suspend fun insertRevenue(revenue: Revenue)
    fun getCurrentYearTotalRevenueStream(currentYear: Int): Flow<Double?>
    fun getCurrentMonthTotalRevenueStream(currentYear: Int, currentMonth: Int): Flow<Double?>
    fun getYearMonthRevenueStream(currentYear: Int): Flow<List<YearMonthRevenue>>

    fun getRevenueStream(id: Int): Flow<Revenue?>
    
    suspend fun deleteRevenue(revenue: Revenue)
    suspend fun deleteByMachineId(machineId: Int)

    suspend fun updateRevenue(revenue: Revenue)

}