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

class ChangeItemFragment: Fragment() {

    private var taskItemID: Int = Task.UNDEFINED_ID
    private val screeMode: String = ChangeItemFragment.MODE_UNKNOW
    private lateinit var viewmodel: ChangeViewModel
    private lateinit var titleInput: TextInputEditText
    private lateinit var desriptionInput: TextInputEditText
    private lateinit var saveButton: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.change_item_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        initViews(view)
        observeView()
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
            activity?.onBackPressed()
        }
    }


    private fun parseParams() {
        if (screeMode != MODE_ADD && screeMode != MODE_EDIT) {
            throw RuntimeException("Param screen mod is absent")
        }

        if (screeMode == MODE_EDIT && taskItemID == Task.UNDEFINED_ID) {
            throw RuntimeException("Param screen mod is absent")
        }
    }



    private fun initViews(view: View) {
        viewmodel = ViewModelProvider(this).get(ChangeViewModel::class.java)
        titleInput = view.findViewById(R.id.title_input)
        desriptionInput = view.findViewById(R.id.description_input)
        saveButton = view.findViewById(R.id.save_button)
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "EXTRA_MODE"
        private const val MODE_ADD = "ADD_MODE"
        private const val MODE_EDIT = "EDIT_MODE"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_task_id"
        private const val MODE_UNKNOW = ""
        fun newInstanceAddItem(): ChangeItemFragment {
            return ChangeItemFragment().apply {
                arguments?.apply {
                    putString(EXTRA_SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(taskItemID: Int): ChangeItemFragment {
            return ChangeItemFragment().apply {
                arguments?.apply {
                    putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                    putInt(EXTRA_SHOP_ITEM_ID,taskItemID)
                }
            }
        }


    }
}