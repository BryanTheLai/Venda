package com.example.venda.data


import android.content.Context



/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val machinesRepository: MachinesRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineMachinesRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [MachinesRepository]
     */
    override val machinesRepository: MachinesRepository by lazy {
        OfflineMachinesRepository(InventoryDatabase.getDatabase(context).machineDao())
    }
}
