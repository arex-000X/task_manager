package com.example.taskmanager.domain.usecase

import com.example.taskmanager.domain.Task
import com.example.taskmanager.domain.repository.TaskRepository

class TaskDeleteItemUseCase(val repository: TaskRepository) {
        fun deleteTaskItem(taskItem: Task) = this.repository.deleteTaskItem(taskItem)
}