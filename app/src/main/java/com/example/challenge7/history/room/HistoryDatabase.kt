package com.example.challenge7.history.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [History::class], version = 1)
abstract class HistoryDatabase : RoomDatabase(){

    abstract fun getHistoryDao(): HistoryDao

//    companion object {
//        private var INSTANCE : HistoryDatabase? = null
//
//        fun getInstance(context: Context): HistoryDatabase?{
//            if (INSTANCE == null){
//                synchronized(HistoryDatabase::class){
//                    INSTANCE = Room.databaseBuilder(context.applicationContext, HistoryDatabase::class.java, " History.db").build()
//                }
//            }
//            return INSTANCE
//        }
//        fun destroyInstance(){
//            INSTANCE = null
//        }
//    }

    companion object {
        @Volatile
        private var instance : HistoryDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context:Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, HistoryDatabase::class.java,"history-database"
        ).build()

    }
}