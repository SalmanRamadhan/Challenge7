package com.example.challenge7.history.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query

@Dao
interface HistoryDao {

    @Query("SELECT * FROM History WHERE userName = :userName")
    suspend fun getHistories(userName: String) : List<History>

    @Query("SELECT * FROM History WHERE id=:id")
    suspend fun getHistory(id: Long):List<History>

    @Insert(onConflict = IGNORE)
    suspend fun insertHistory(history: History) : Long

    @Delete
    suspend fun deleteHistory(history: History) : Int

}