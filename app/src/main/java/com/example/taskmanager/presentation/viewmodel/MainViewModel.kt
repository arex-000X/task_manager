package com.example.taskmanager.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.TaskRepositoryImpl
import com.example.taskmanager.domain.Task
import com.example.taskmanager.domain.usecase.TaskDeleteItemUseCase
import com.example.taskmanager.domain.usecase.TaskEditItemUseCase
import com.example.taskmanager.domain.usecase.TaskListUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TaskRepositoryImpl(application)
    private val taskListUseCase = TaskListUseCase(repository)
    private val deleteTaskUseCase = TaskDeleteItemUseCase(repository)
    private val editTaskUseCase = TaskEditItemUseCase(repository)
    val taskList = taskListUseCase.getTaskList()

    fun deleteTaskItem(taskItem: Task) {
        viewModelScope.launch {
            deleteTaskUseCase.deleteTaskItem(taskItem)
        }

    }

    fun enableChanged(taskItem: Task) {
        viewModelScope.launch {
            val newite = taskItem.copy(enable = !taskItem.enable)
            editTaskUseCase.editItemUseCase(newite)
        }

    }

}