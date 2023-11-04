package com.example.taskmanager.domain.repository

import androidx.lifecycle.LiveData
import com.example.taskmanager.domain.Task

interface TaskRepository {

    fun getTaskList(): LiveData<List<Task>>
    suspend fun getTaskItem(id: Int): Task
    suspend fun addTaskItem(taskItem: Task)
    suspend fun deleteTaskItem(taskItem: Task)
    suspend fun editTaskItem(taskItem: Task)
}