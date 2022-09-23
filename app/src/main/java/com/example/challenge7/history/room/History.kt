package com.example.challenge7.history.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity

data class History(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo var hasilPermainan : String,
    @ColumnInfo var hasilPermainan2 : String,
    @ColumnInfo var date : String,
    @ColumnInfo var time : String
)