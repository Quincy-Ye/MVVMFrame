package com.yeqingqing.mvvmframe.demo.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase




@Database(entities = [MainDataBean::class], version = 1, exportSchema = true)
abstract class  MainDatabase :RoomDatabase() {

    abstract fun mainDao(): MainDao
    companion object {
        @JvmStatic
        @Volatile
        private var INSTANCE: MainDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MainDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "record_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}