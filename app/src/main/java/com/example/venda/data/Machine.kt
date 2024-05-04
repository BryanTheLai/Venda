package com.example.venda.data

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "machines")
data class Machine(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val capacity: Int,
    val model: String, // model
    // Second Option
    val year: Int, // Record year only
    val month: Int, // Record month only
    val day: Int, // Record day only
    val location: String, // location
    val currentStatus: String, // e.g., "operational", "out_of_stock", "out_of_service"
    val serialNumber: String,
    )
