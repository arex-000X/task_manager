package com.example.taskmanager.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.presentation.adapter.TaskAdapter
import com.example.taskmanager.presentation.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),ChangeItemFragment.EdingFragment {

    private lateinit var viewmodel: MainViewModel
    private lateinit var recylerview: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var floatingActionButton: FloatingActionButton
    private var taskfragmentContainer: FragmentContainerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
      //  Log.d("TaskDebbuger", "MainActivity onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        taskfragmentContainer = findViewById(R.id.fragment_container_view_act)
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
                if (isOnPaneMode()) {
                    val intent = ChangeItemActivity.newIntentEditItem(this, it.id)
                    startActivity(intent)
                } else {
                    launchFragment(ChangeItemFragment.newInstanceEditItem(it.id))
                }

            }
        }
    }

    private fun setupFloatingActionButton() {
        floatingActionButton.setOnClickListener {
            if (isOnPaneMode()) {
                val intent = ChangeItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ChangeItemFragment.newInstanceAddItem(taskId))
            }
        }
    }

    private fun launchFragment(fm: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction().addToBackStack(null)
            .replace(R.id.fragment_container_view_act, fm)
            .commit()
    }
    private fun isOnPaneMode(): Boolean {
        return taskfragmentContainer == null
    }

    fun initViews() {
        viewmodel = ViewModelProvider(this)[MainViewModel::class.java]
        adapter = TaskAdapter()
        floatingActionButton = findViewById(R.id.floatingActionButton)

    }

    override fun closeFragment() {
        supportFragmentManager.popBackStack()
    }
}