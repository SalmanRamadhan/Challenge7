package com.example.challenge7.history.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [History::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun getHistoryDao(): HistoryDao

    companion object {
        private var database: HistoryDatabase? = null

        fun instance(context: Context): HistoryDatabase {
            synchronized(HistoryDatabase::class) {
                if (database == null) {
                    database = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "jankenpon.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return database!!
        }
    }
}