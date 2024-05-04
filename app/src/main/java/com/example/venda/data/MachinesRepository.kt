package com.example.venda.data

import kotlinx.coroutines.flow.Flow

interface MachinesRepository {

    fun getAllMachinesStream(): Flow<List<Machine>>
    fun getMachineStatusCountsStream(): Flow<List<MachineStatusCount>>

    fun getMachineStream(id: Int): Flow<Machine?>

    suspend fun insertMachine(machine: Machine)

    suspend fun deleteMachine(machine: Machine)

    suspend fun updateMachine(machine: Machine)
}
