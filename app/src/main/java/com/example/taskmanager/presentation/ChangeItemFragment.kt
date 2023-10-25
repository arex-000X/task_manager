package com.example.taskmanager.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.R
import com.example.taskmanager.domain.Task
import com.example.taskmanager.presentation.viewmodel.ChangeViewModel
import com.google.android.material.textfield.TextInputEditText
import java.lang.RuntimeException

class ChangeItemFragment : Fragment() {

    private var taskItemID: Int = Task.UNDEFINED_ID
    private var screeMode: String = MODE_UNKNOW
    private lateinit var viewmodel: ChangeViewModel
    private lateinit var titleInput: TextInputEditText
    private lateinit var desriptionInput: TextInputEditText
    private lateinit var saveButton: Button
    private lateinit var closeScreenFragment:EdingFragment


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("TaskDebbuger", "ChangeItemFragment   onAttach()")
        if (context is EdingFragment){
            closeScreenFragment = context
        }else{
            throw RuntimeException("Activity error")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TaskDebbuger", "ChangeItemFragment  onCreate()")
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TaskDebbuger", "ChangeItemFragment  onCreateView()")
        return inflater.inflate(R.layout.change_item_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TaskDebbuger", "ChangeItemFragment  onViewCreated()")
        initViews(view)
        observeView()
        launchRightMode()
    }

    override fun onStart() {
        super.onStart()
        Log.d("TaskDebbuger", "ChangeItemFragment  onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TaskDebbuger", "ChangeItemFragment onResume()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TaskDebbuger", "ChangeItemFragment onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TaskDebbuger", "ChangeItemFragment onDestroyView()")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("TaskDebbuger", "ChangeItemFragment onDetach()")
    }
    private fun observeView() {

        viewmodel.getErrorTitleEditTex.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_title)
            } else {
                null
            }
            titleInput.error = message
        }
        viewmodel.getErrorDescriptionEditTex.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_title)
            } else {
                null
            }
            desriptionInput.error = message
        }

        viewmodel.getCloseScreen.observe(viewLifecycleOwner) {
            closeScreenFragment.closeFragment()
        }
    }

    private fun launchRightMode() {
        when (screeMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        saveButton.setOnClickListener {
            viewmodel.getTaskItem(taskItemID)
            viewmodel.getEditItemTask(titleInput.text.toString(), desriptionInput.text.toString())
        }
    }

    private fun launchAddMode() {
        saveButton.setOnClickListener {
            viewmodel.getAddItemTask(titleInput.text.toString(), desriptionInput.text.toString())
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mod is absent")
        }

        val mode = args.getString(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknow screen $mode")
        }

        screeMode = mode
        if (screeMode == MODE_EDIT) {
            if (!args.containsKey(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param screen mod is absent")
            }
            taskItemID = args.getInt(EXTRA_SHOP_ITEM_ID, Task.UNDEFINED_ID)
        }
    }


    private fun initViews(view: View) {
        viewmodel = ViewModelProvider(this).get(ChangeViewModel::class.java)
        titleInput = view.findViewById(R.id.title_input)
        desriptionInput = view.findViewById(R.id.description_input)
        saveButton = view.findViewById(R.id.save_button)
    }




    interface EdingFragment{
        fun closeFragment()
    }
    companion object {
        private const val EXTRA_SCREEN_MODE = "EXTRA_MODE"
        private const val MODE_ADD = "ADD_MODE"
        private const val MODE_EDIT = "EDIT_MODE"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_task_id"
        private const val MODE_UNKNOW = ""
        fun newInstanceAddItem(): ChangeItemFragment {
            return ChangeItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(taskItemID: Int): ChangeItemFragment {
            return ChangeItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                    putInt(EXTRA_SHOP_ITEM_ID, taskItemID)
                }
            }
        }


    }
}