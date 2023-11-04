package com.example.taskmanager.data

import android.app.Application
import android.service.autofill.Transformation
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.taskmanager.domain.Task
import com.example.taskmanager.domain.repository.TaskRepository
import java.lang.RuntimeException

class TaskRepositoryImpl(
    application: Application
) : TaskRepository {

    private val taskDB = TaskDatabase.getInstance(application).taskDao()
    private val mapper = TaskMapper()
    override fun getTaskList(): LiveData<List<Task>> = taskDB.getTaskList().map {
        mapper.mapListDBMidule(it)
    }


    override suspend fun getTaskItem(id: Int): Task {
        val modelDb = taskDB.getTaskItem(id)
        return mapper.taskDBModuleToTask(modelDb)
    }

    override suspend fun addTaskItem(taskItem: Task) {
        taskDB.addTaskItem(mapper.taskToTaskDBModule(taskItem))
    }

    override suspend fun deleteTaskItem(taskItem: Task) {
        taskDB.deleteTaskItem(taskItem.id)

    }

    override suspend fun editTaskItem(taskItem: Task) {
        taskDB.addTaskItem(mapper.taskToTaskDBModule(taskItem))
    }


}