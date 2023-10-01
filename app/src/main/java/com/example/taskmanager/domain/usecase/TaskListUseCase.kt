package com.example.taskmanager.domain.usecase

import androidx.lifecycle.LiveData
import com.example.taskmanager.domain.Task
import com.example.taskmanager.domain.repository.TaskRepository

class TaskListUseCase(private val repository: TaskRepository) {
    fun getTaskList():LiveData<List<Task>> = this.repository.getTaskList()
}