package com.example.taskmanager.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskmanager.domain.Task
import com.example.taskmanager.domain.repository.TaskRepository
import java.lang.RuntimeException

object TaskRepositoryImpl : TaskRepository {

    val task = sortedSetOf<Task>({ o1, o2 -> o1.id.compareTo(o2.id) })
    val taskListMutable = MutableLiveData<List<Task>>()
    private var autoIncrementId = 0


    override fun getTaskList(): LiveData<List<Task>> {

        return taskListMutable
    }

    override fun getTaskItem(id: Int): Task {
        return task.find {
            it.id == id
        } ?: throw RuntimeException("Element with id $id not found")
    }

    override fun addTaskItem(taskItem: Task) {

        if (taskItem.id == Task.UNDEFINED_ID) {
            taskItem.id = autoIncrementId++
        }

        Log.d("TaskDebbuger", "${taskItem}")
        task.add(taskItem)
        updateList()
    }

    override fun deleteTaskItem(taskItem: Task) {
        task.remove(taskItem)
        updateList()
    }

    override fun editTaskItem(taskItem: Task) {
        val oldTaskItem = getTaskItem(taskItem.id)
        task.remove(oldTaskItem)
        addTaskItem(taskItem)
        updateList()
    }

    fun updateList() {
        taskListMutable.value = task.toList()
    }
}