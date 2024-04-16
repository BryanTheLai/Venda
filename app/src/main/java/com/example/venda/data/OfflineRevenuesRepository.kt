package com.example.venda.data

import kotlinx.coroutines.flow.Flow

class OfflineRevenuesRepository(private val revenueDao: RevenueDao) : RevenuesRepository {
    override fun getRevenuesForMachineStream(machineId: Int): Flow<List<Revenue>> =
        revenueDao.getRevenuesForMachine(machineId)
    override fun getAllRevenuesStream(): Flow<List<Revenue>> =
        revenueDao.getAllRevenues()

    override suspend fun insertRevenue(revenue: Revenue) = revenueDao.insert(revenue)
}