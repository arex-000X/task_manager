package com.example.taskmanager.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.taskmanager.R
import com.example.taskmanager.databinding.RecylerItemDisableBinding
import com.example.taskmanager.databinding.RecylerItemEnableBinding
import com.example.taskmanager.domain.Task
import java.lang.RuntimeException


class TaskAdapter : ListAdapter<Task, TaskViewHolder>(DiffItemAdapter()) {

    var setLongClickListene: ((Task) -> Unit)? = null
    var setOnClickListene: ((Task) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLE -> R.layout.recyler_item_enable
            VIEW_TYPE_DISABLE -> R.layout.recyler_item_disable
            else -> throw RuntimeException("Unknow view type $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return TaskViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enable) {
            0
        } else {
            1
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task: Task = getItem(position)
        val binding = holder.binding

        when (binding) {
            is RecylerItemEnableBinding -> {
                binding.title.text = task.title
                binding.description.text = task.description
            }

            is RecylerItemDisableBinding -> {
                binding.title.text = task.title
                binding.description.text = task.description
            }
        }
        holder.itemView.setOnLongClickListener {
            setLongClickListene?.invoke(task)
            true
        }
        holder.itemView.setOnClickListener {
            setOnClickListene?.invoke(task)
        }

    }

    companion object {
        const val VIEW_TYPE_ENABLE = 0
        const val VIEW_TYPE_DISABLE = 1

    }

}