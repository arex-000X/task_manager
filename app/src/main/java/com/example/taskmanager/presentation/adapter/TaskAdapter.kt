package com.example.taskmanager.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.taskmanager.R
import com.example.taskmanager.domain.Task
import java.lang.RuntimeException



class TaskAdapter : ListAdapter<Task, TaskViewHolder>(DiffItemAdapter()) {

        var setLongClickListene:((Task) -> Unit)? = null
        var setOnClickListene:((Task) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLE -> R.layout.recyler_item_enable
            VIEW_TYPE_DISABLE -> R.layout.recyler_item_disable
            else -> throw RuntimeException("Unknow view type $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if(item.enable){
            0
        }else{
            1
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task: Task = getItem(position)
        holder.title.text = task.title
        holder.description.text = task.description
        holder.itemView.setOnLongClickListener {
            setLongClickListene?.invoke(task)
            true
        }
        holder.itemView.setOnClickListener {
            setOnClickListene?.invoke(task)
        }

    }
    companion object{
        const val VIEW_TYPE_ENABLE = 0
        const val VIEW_TYPE_DISABLE = 1

    }

}