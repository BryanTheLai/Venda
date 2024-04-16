package com.example.venda.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "revenues")
data class Revenue (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val machineId: Int, // ID of Machine Machine.id
    val revenue: Double,
    // Second Option
    val year: Int, // Record year only
    val month: Int, // Record month only
)