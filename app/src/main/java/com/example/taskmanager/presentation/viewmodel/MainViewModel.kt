package com.example.taskmanager.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.taskmanager.data.TaskRepositoryImpl
import com.example.taskmanager.domain.Task
import com.example.taskmanager.domain.usecase.TaskDeleteItemUseCase
import com.example.taskmanager.domain.usecase.TaskEditItemUseCase
import com.example.taskmanager.domain.usecase.TaskListUseCase

class MainViewModel : ViewModel() {

    private val repository = TaskRepositoryImpl
    private val taskListUseCase = TaskListUseCase(repository)
    private val deleteTaskUseCase = TaskDeleteItemUseCase(repository)
    private val editTaskUseCase = TaskEditItemUseCase(repository)
    val taskList = taskListUseCase.getTaskList()

    fun deleteTaskItem(taskItem: Task) {
        deleteTaskUseCase.deleteTaskItem(taskItem)
    }

    fun enableChanged(taskItem: Task) {
        val newite = taskItem.copy(enable = !taskItem.enable)
        return editTaskUseCase.editItemUseCase(newite)
    }

}