package com.example.venda.data

import kotlinx.coroutines.flow.Flow

interface MachinesRepository {
    /**
     * Retrieve all the machines from the given data source.
     */
    fun getAllMachinesStream(): Flow<List<Machine>>

    /**
     * Retrieve an machine from the given data source that matches with the [id].
     */
    fun getMachineStream(id: Int): Flow<Machine?>

    /**
     * Insert machine in the data source
     */
    suspend fun insertMachine(machine: Machine)

    /**
     * Delete machine from the data source
     */
    suspend fun deleteMachine(machine: Machine)

    /**
     * Update machine in the data source
     */
    suspend fun updateMachine(machine: Machine)
}
