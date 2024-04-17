package com.example.venda.data

import kotlinx.coroutines.flow.Flow

class OfflineRevenuesRepository(private val revenueDao: RevenueDao) : RevenuesRepository {
    override fun getRevenuesForRevenueStream(revenueId: Int): Flow<List<Revenue>> =
        revenueDao.getRevenuesForRevenue(revenueId)
    override fun getAllRevenuesStream(): Flow<List<Revenue>> =
        revenueDao.getAllRevenues()
    override fun getAllRevenuesForMachine(machineId: Int): Flow<List<Revenue>> =
        revenueDao.getRevenuesForMachine(machineId)

    override suspend fun insertRevenue(revenue: Revenue) = revenueDao.insert(revenue)
    
    override fun getRevenueStream(id: Int): Flow<Revenue?> = revenueDao.getRevenue(id)

    override suspend fun deleteRevenue(revenue: Revenue) = revenueDao.delete(revenue)

    override suspend fun updateRevenue(revenue: Revenue) = revenueDao.update(revenue)
}