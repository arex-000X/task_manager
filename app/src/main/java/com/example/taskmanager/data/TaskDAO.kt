package com.example.taskmanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDAO {
    @Query("SELECT * FROM task_database")
     fun getTaskList(): LiveData<List<TaskDBModule>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTaskItem(taskDBModule: TaskDBModule)

    @Query("DELETE FROM task_database WHERE id=:taskId ")
    suspend fun deleteTaskItem(taskId: Int)

    @Query("SELECT * FROM task_database WHERE id=:taskId LIMIT 1 ")
    suspend fun getTaskItem(taskId: Int): TaskDBModule
}