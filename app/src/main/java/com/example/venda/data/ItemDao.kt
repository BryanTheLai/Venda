package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MachineDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(machine: Machine)

    @Update
    suspend fun update(machine: Machine)

    @Delete
    suspend fun delete(machine: Machine)

    @Query("SELECT * from machines WHERE id = :id")
    fun getMachine(id: Int): Flow<Machine>

    @Query("SELECT * from machines ORDER BY name ASC")
    fun getAllMachines(): Flow<List<Machine>>
}