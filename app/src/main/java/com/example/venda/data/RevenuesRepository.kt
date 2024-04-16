package com.example.venda.data

import kotlinx.coroutines.flow.Flow

interface RevenuesRepository {
    fun getRevenuesForMachineStream(machineId: Int): Flow<List<Revenue>>
    fun getAllRevenuesStream(): Flow<List<Revenue>>


    suspend fun insertRevenue(revenue: Revenue)

}