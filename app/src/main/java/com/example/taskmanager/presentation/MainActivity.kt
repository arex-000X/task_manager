package com.example.taskmanager.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.presentation.adapter.TaskAdapter
import com.example.taskmanager.presentation.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel: MainViewModel
    private lateinit var recylerview: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var floatingActionButton: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        recylerView()
        viewModel()
        setupLongListener()
        setupSwipeListener()
        setupOnClickListener()
        setupFloatingActionButton()
    }

    private fun viewModel() {
        viewmodel.apply {
            taskList.observe(this@MainActivity) {
                adapter.submitList(it)
            }
        }
    }

    private fun recylerView() {
        recylerview = findViewById(R.id.recylerview)
        recylerview.adapter = adapter
    }


    private fun setupSwipeListener() {
        val callback = object : ItemTouchHelper
        .SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewmodel.deleteTaskItem(item)
            }


        }
        val item = ItemTouchHelper(callback)
        item.attachToRecyclerView(recylerview)

    }

    private fun setupLongListener() {
        adapter.setLongClickListene = {
            viewmodel.enableChanged(it)
        }
    }

    private fun setupOnClickListener() {
        adapter.setOnClickListene = {
            if (it.enable == true) {
                val intent = ChangeItemActivity.newIntentEditItem(this,it.id)
                startActivity(intent)
            }
        }
    }

    private fun setupFloatingActionButton() {
        floatingActionButton.setOnClickListener {
            val intent = ChangeItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }


    fun initViews() {
        viewmodel = ViewModelProvider(this)[MainViewModel::class.java]
        adapter = TaskAdapter()
        floatingActionButton = findViewById(R.id.floatingActionButton)

    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}