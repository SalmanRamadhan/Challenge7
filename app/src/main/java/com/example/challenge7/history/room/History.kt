package com.example.challenge7.history.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class History(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo var hasilPermainan : String,
    @ColumnInfo var modePermainan : String,
    @ColumnInfo var date : String,
    @ColumnInfo var time : String,
    @ColumnInfo var userName : String,
    @ColumnInfo var timeStamp : Long
) : Parcelable