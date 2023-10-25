package com.example.taskmanager.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.R
import com.example.taskmanager.domain.Task
import com.example.taskmanager.presentation.viewmodel.ChangeViewModel
import com.google.android.material.textfield.TextInputEditText
import java.lang.RuntimeException

class ChangeItemActivity : AppCompatActivity(), ChangeItemFragment.EdingFragment {
    private var screeMode = MODE_UNKNOW
    private var taskItemID = Task.UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
      //  Log.d("TaskDebbuger", "ChangeItemActivity onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_item_activity)
        parseIntent()
        launchRightMode()
    }


    override fun onStart() {
        super.onStart()
      //  Log.d("TaskDebbuger", "ChangeItemActivity onStart()")
    }

    override fun onResume() {
        super.onResume()
       // Log.d("TaskDebbuger", "ChangeItemActivity onResume()")
    }

    override fun onStop() {
        super.onStop()
       // Log.d("TaskDebbuger", "ChangeItemActivity  onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
       // Log.d("TaskDebbuger", "ChangeItemActivity onDestroy()")
    }


    private fun launchRightMode() {
        val fragment = when (screeMode) {
            MODE_EDIT -> ChangeItemFragment.newInstanceEditItem(taskItemID)
            MODE_ADD -> ChangeItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknow screen ")
        }
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view, fragment)
            .commit()
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

    override fun closeFragment() {
        finish()
    }
}