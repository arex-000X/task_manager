package com.example.taskmanager.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.taskmanager.domain.Task

class DiffItemAdapter: DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }
}