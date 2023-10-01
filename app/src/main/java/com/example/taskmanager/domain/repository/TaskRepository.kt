package com.example.taskmanager.domain.repository

import androidx.lifecycle.LiveData
import com.example.taskmanager.domain.Task

interface TaskRepository {

   fun getTaskList():LiveData<List<Task>>
   fun getTaskItem(id:Int): Task
   fun addTaskItem(taskItem: Task)
   fun deleteTaskItem(taskItem: Task)
   fun editTaskItem(taskItem: Task)
}