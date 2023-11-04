package com.example.taskmanager.data

import com.example.taskmanager.domain.Task

class TaskMapper {
    fun taskToTaskDBModule(task: Task) = TaskDBModule(
        id = task.id,
        title = task.title,
        description = task.description,
        enable = task.enable
    )

    fun taskDBModuleToTask(taskDBModule: TaskDBModule) = Task(
        id = taskDBModule.id,
        title = taskDBModule.title,
        description = taskDBModule.description,
        enable = taskDBModule.enable
    )

    fun mapListDBMidule(lsit: List<TaskDBModule>) = lsit.map {
        taskDBModuleToTask(it)
    }
}