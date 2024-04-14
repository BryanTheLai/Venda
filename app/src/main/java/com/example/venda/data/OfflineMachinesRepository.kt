package com.example.venda.data

import kotlinx.coroutines.flow.Flow

class OfflineMachinesRepository(private val machineDao: MachineDao) : MachinesRepository {
    override fun getAllMachinesStream(): Flow<List<Machine>> = machineDao.getAllMachines()

    override fun getMachineStream(id: Int): Flow<Machine?> = machineDao.getMachine(id)

    override suspend fun insertMachine(machine: Machine) = machineDao.insert(machine)

    override suspend fun deleteMachine(machine: Machine) = machineDao.delete(machine)

    override suspend fun updateMachine(machine: Machine) = machineDao.update(machine)
}