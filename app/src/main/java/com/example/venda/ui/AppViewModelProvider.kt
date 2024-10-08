package com.example.venda.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.venda.InventoryApplication
import com.example.venda.ui.csv.CsvViewModel
import com.example.venda.ui.dashboard.DashboardViewModel
import com.example.venda.ui.home.HomeViewModel
import com.example.venda.ui.home.RevenueViewModel
import com.example.venda.ui.item.MachineDetailsViewModel
import com.example.venda.ui.item.MachineEditViewModel
import com.example.venda.ui.item.MachineEntryViewModel
import com.example.venda.ui.item.RevenueDetailsViewModel
import com.example.venda.ui.item.RevenueEditViewModel
import com.example.venda.ui.item.RevenueEntryViewModel


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
        initializer {
            RevenueEditViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.revenuesRepository
            )
        }
        // Initializer for MachineEntryViewModel
        initializer {
            MachineEntryViewModel(inventoryApplication().container.machinesRepository)
        }

        initializer {
            RevenueEntryViewModel(this.createSavedStateHandle(),inventoryApplication().container.revenuesRepository)
        }

        // Initializer for MachineDetailsViewModel
        initializer {
            MachineDetailsViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.machinesRepository,
                inventoryApplication().container.revenuesRepository

            )
        }
        initializer {
            RevenueDetailsViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.revenuesRepository
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(inventoryApplication().container.machinesRepository)
        }
        // Initializer for CsvViewModel
        initializer {
            CsvViewModel(inventoryApplication().container.machinesRepository)
        }

        // Initializer for RevenueViewModel
        initializer {
            RevenueViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.revenuesRepository)
        }

        initializer {
            DashboardViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.machinesRepository,
                inventoryApplication().container.revenuesRepository)
        }


    }
}

fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)
