package com.example.taskmanager.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TaskDBModule::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDAO


    companion object {
        private var INSTANCE: TaskDatabase? = null
        private val LOCK = Any()
        fun getInstance(application: Application): TaskDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
            }
            val database = Room.databaseBuilder(
                application,
                TaskDatabase::class.java, "task_table"
            )
                .build()
            INSTANCE = database

            return database
        }
    }
}