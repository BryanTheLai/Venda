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


    @Query("SELECT * from revenues WHERE id = :id ORDER BY year DESC, month ASC")
    fun getRevenuesForRevenue(id: Int): Flow<List<Revenue>>

    @Query("SELECT * from revenues WHERE machineId = :machineId ORDER BY year DESC, month ASC")
    fun getRevenuesForMachine(machineId: Int): Flow<List<Revenue>>
    
    @Query("SELECT * from revenues ORDER BY year DESC, month ASC")
    fun getAllRevenues(): Flow<List<Revenue>>

    @Query("SELECT SUM(revenue) AS totalRevenue FROM revenues WHERE year = :currentYear")
    fun getCurrentYearTotalRevenue(currentYear: Int = 2024): Flow<Double?>

    @Query("SELECT SUM(revenue) AS totalRevenue FROM revenues WHERE year = :currentYear AND month = :currentMonth")
    fun getCurrentMonthTotalRevenue(currentYear: Int = 2024, currentMonth: Int = 1): Flow<Double?>

    @Query("SELECT year, month, SUM(revenue) AS totalRevenue FROM revenues  WHERE year = :currentYear  GROUP BY year, month")
    fun getYearMonthRevenue(currentYear: Int): Flow<List<YearMonthRevenue>>


    @Update
    suspend fun update(revenue: Revenue)

    @Delete
    suspend fun delete(revenue: Revenue)

    @Query("SELECT * from revenues WHERE id = :id ORDER BY year DESC, month ASC")
    fun getRevenue(id: Int): Flow<Revenue>

    @Query("DELETE FROM revenues WHERE machineId = :machineId")
    suspend fun deleteByMachineId(machineId: Int)

}

data class YearMonthRevenue(val year: Int = 2024, val month: Int = 1, val totalRevenue: Double = 123.45)
