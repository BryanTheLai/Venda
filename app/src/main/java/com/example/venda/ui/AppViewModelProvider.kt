/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
