package com.example.taskmanager.domain.usecase

import com.example.taskmanager.domain.Task
import com.example.taskmanager.domain.repository.TaskRepository

class TaskAddItemUseCase(private val repository: TaskRepository) {
  suspend fun addTaskItem(taskItem: Task) = this.repository.addTaskItem(taskItem)
}