package com.example.challenge7.history.room

import androidx.room.*
import java.time.LocalDate
import java.time.LocalTime

@Entity

data class History(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo var hasilPermainan : String,
    @ColumnInfo var date : String,
    @ColumnInfo var time : String,
    @ColumnInfo var userName : String,
//    @Ignore var delete: Delete
)