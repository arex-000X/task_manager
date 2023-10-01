package com.example.taskmanager.domain.usecase

import androidx.lifecycle.LiveData
import com.example.taskmanager.domain.Task
import com.example.taskmanager.domain.repository.TaskRepository

class TaskItemUseCase(private val repository: TaskRepository) {
    fun getItemTask(id:Int):Task = this.repository.getTaskItem(id)
}