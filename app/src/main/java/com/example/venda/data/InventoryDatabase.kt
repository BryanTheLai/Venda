package com.example.venda.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Machine::class, Revenue::class], version = 6, exportSchema = false)
abstract class InventoryDatabase: RoomDatabase() {
    abstract fun machineDao(): MachineDao
    abstract fun revenueDao(): RevenueDao


    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null
        fun getDatabase(context: Context): InventoryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "machine_database")
                    .fallbackToDestructiveMigration()
                    .build().also{ Instance = it}
            }
        }
    }

}