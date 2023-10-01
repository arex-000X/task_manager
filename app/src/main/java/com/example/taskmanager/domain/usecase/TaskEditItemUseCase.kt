package com.example.taskmanager.domain.usecase

import com.example.taskmanager.domain.Task
import com.example.taskmanager.domain.repository.TaskRepository

class TaskEditItemUseCase(private val repository: TaskRepository) {
    fun editItemUseCase(taskItem:Task) = this.repository.editTaskItem(taskItem)
}