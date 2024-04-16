package com.example.venda.data


import android.content.Context

interface AppContainer {
    val machinesRepository: MachinesRepository
    val revenuesRepository: RevenuesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val machinesRepository: MachinesRepository by lazy {
        OfflineMachinesRepository(InventoryDatabase.getDatabase(context).machineDao())
    }

    override val revenuesRepository: RevenuesRepository by lazy {
        OfflineRevenuesRepository(InventoryDatabase.getDatabase(context).revenueDao())
    }
}
