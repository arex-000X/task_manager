package com.example.taskmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskmanager.domain.Task
@Entity(tableName = "task_database")
data class TaskDBModule(
    @PrimaryKey(true)
    var id: Int,
    val title: String,
    val description: String,
    val enable:Boolean,

){

}
