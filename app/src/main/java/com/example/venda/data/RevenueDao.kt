package com.example.venda.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RevenueDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(revenue: Revenue)


    @Query("SELECT * from revenues WHERE id = :id ORDER BY year, month ASC")
    fun getRevenuesForRevenue(id: Int): Flow<List<Revenue>>

    @Query("SELECT * from revenues WHERE machineId = :machineId ORDER BY year, month ASC")
    fun getRevenuesForMachine(machineId: Int): Flow<List<Revenue>>
    
    @Query("SELECT * from revenues ORDER BY year, month ASC")
    fun getAllRevenues(): Flow<List<Revenue>>


    @Update
    suspend fun update(revenue: Revenue)

    @Delete
    suspend fun delete(revenue: Revenue)

    @Query("SELECT * from revenues WHERE id = :id")
    fun getRevenue(id: Int): Flow<Revenue>




}