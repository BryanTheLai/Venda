package com.example.venda.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.venda.InventoryApplication
import com.example.venda.ui.home.HomeViewModel
import com.example.venda.ui.item.MachineDetailsViewModel
import com.example.venda.ui.item.MachineEditViewModel
import com.example.venda.ui.item.MachineEntryViewModel


/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for MachineEditViewModel
        initializer {
            MachineEditViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.machinesRepository
            )
        }
        // Initializer for MachineEntryViewModel
        initializer {
            MachineEntryViewModel(inventoryApplication().container.machinesRepository)
        }

        // Initializer for MachineDetailsViewModel
        initializer {
            MachineDetailsViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.machinesRepository
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(inventoryApplication().container.machinesRepository)
        }
    }
}

fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)
