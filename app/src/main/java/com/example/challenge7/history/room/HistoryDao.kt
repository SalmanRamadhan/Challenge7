package com.example.challenge7.history.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface HistoryDao {

    @Query("SELECT * FROM History ORDER BY id DESC")
    suspend fun getHistory() : List<History>

    @Insert(onConflict = REPLACE)
    suspend fun insertHistory(history: History) : Long

    @Delete
    suspend fun deleteHistory(history: History) : Int

}