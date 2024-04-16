package com.example.venda.data

import kotlinx.coroutines.flow.Flow

interface RevenuesRepository {
    fun getRevenuesForRevenueStream(revenueId: Int): Flow<List<Revenue>>

    fun getAllRevenuesStream(): Flow<List<Revenue>>

    suspend fun insertRevenue(revenue: Revenue)
    
    fun getRevenueStream(id: Int): Flow<Revenue?>
    
    suspend fun deleteRevenue(revenue: Revenue)

    suspend fun updateRevenue(revenue: Revenue)

}