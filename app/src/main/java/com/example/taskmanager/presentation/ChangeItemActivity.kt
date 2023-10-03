package com.example.taskmanager.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.R
import com.example.taskmanager.domain.Task
import com.example.taskmanager.presentation.viewmodel.ChangeViewModel
import com.google.android.material.textfield.TextInputEditText
import java.lang.RuntimeException

class ChangeItemActivity : AppCompatActivity() {
    private var screeMode = MODE_UNKNOW
    private var taskItemID = Task.UNDEFINED_ID
    lateinit var viewmodel: ChangeViewModel
    lateinit var titleInput: TextInputEditText
    lateinit var desriptionInput: TextInputEditText
    lateinit var saveButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_item_activity)
        parseIntent()
        initViews()
        launchRightMode()
    }

    private fun launchRightMode() {
        when (screeMode) {
            MODE_EDIT -> launchEditItem()
            MODE_ADD -> launchAddItem()
        }
    }

    private fun launchEditItem() {
        viewmodel.apply {
            saveButton.setOnClickListener {
                getTaskItem(taskItemID)
                getEditItemTask(titleInput.text.toString(), desriptionInput.text.toString())
            }
        }
    }

    private fun launchAddItem() {
        viewmodel.apply {
            saveButton.setOnClickListener {
                getAddItemTask(titleInput.text.toString(),desriptionInput.text.toString())
            }

        }
    }


    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mod is absent")
        }

        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknow screen $mode")
        }

        screeMode = mode
        if (screeMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param screen mod is absent")
            }
            taskItemID = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, Task.UNDEFINED_ID)
            Log.d("TaskDebbuger", "parseInt $taskItemID")
        }
    }

    private fun initViews() {
        viewmodel = ViewModelProvider(this@ChangeItemActivity).get(ChangeViewModel::class.java)
        titleInput = findViewById(R.id.title_input)
        desriptionInput = findViewById(R.id.description_input)
        saveButton = findViewById(R.id.save_button)
    }


    companion object {
        private const val EXTRA_SCREEN_MODE = "EXTRA_MODE"
        private const val MODE_ADD = "ADD_MODE"
        private const val MODE_EDIT = "EDIT_MODE"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_task_id"
        private const val MODE_UNKNOW = ""
        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ChangeItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, id: Int): Intent {
            val intent = Intent(context, ChangeItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, id)
            return intent
        }
    }
}