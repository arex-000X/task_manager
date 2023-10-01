package com.example.taskmanager.domain


data class Task(
    val title: String,
    val description: String,
    val enable:Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {

        const val UNDEFINED_ID = -1
    }
}