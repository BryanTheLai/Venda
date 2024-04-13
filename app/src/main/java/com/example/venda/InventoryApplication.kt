package com.example.venda

import android.app.Application
import com.example.venda.data.AppContainer
import com.example.venda.data.AppDataContainer


class InventoryApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}