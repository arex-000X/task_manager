package com.example.taskmanager.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title = view.findViewById<TextView>(R.id.title)
    val description = view.findViewById<TextView>(R.id.description)
}