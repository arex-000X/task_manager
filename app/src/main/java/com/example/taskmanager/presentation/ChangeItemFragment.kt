package com.example.taskmanager.presentation

import android.content.Context
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.R
import com.example.taskmanager.databinding.ChangeItemFragmentBinding
import com.example.taskmanager.domain.Task
import com.example.taskmanager.presentation.viewmodel.ChangeViewModel
import kotlin.RuntimeException

class ChangeItemFragment : Fragment() {

    private var taskItemID: Int = Task.UNDEFINED_ID
    private var screeMode: String = MODE_UNKNOW
    private lateinit var viewmodel: ChangeViewModel
    private lateinit var closeScreenFragment: EdingFragment
    private var _binding: ChangeItemFragmentBinding? = null
    private val binding: ChangeItemFragmentBinding
        get() = _binding ?: throw RuntimeException("FragmentShopItemBinding == null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EdingFragment) {
            closeScreenFragment = context
        } else {
            throw RuntimeException("Activity error")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ChangeItemFragmentBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeView()
        launchRightMode()
    }


    private fun observeView() {

            viewmodel.getErrorTitleEditTex.observe(viewLifecycleOwner) {
                val message = if (it) {
                    getString(R.string.error_input_title)
                } else {
                    null
                }
                binding.titleInput.error = message
            }
            viewmodel.getErrorDescriptionEditTex.observe(viewLifecycleOwner) {
                val message = if (it) {
                    getString(R.string.error_input_title)
                } else {
                    null
                }
                binding.descriptionInput.error = message
            }

            viewmodel.getCloseScreen.observe(viewLifecycleOwner) {
                closeScreenFragment.closeFragment()
            }

    }

    private fun launchRightMode() {
        try {
            when (screeMode) {
                MODE_EDIT -> launchEditMode()
                MODE_ADD -> launchAddMode()
            }
        } catch (error:Exception){
            throw RuntimeException("Ошибка ${error.message}")
        }

    }

    private fun launchEditMode() {

        binding.saveButton.setOnClickListener {
                viewmodel.getTaskItem(taskItemID)
                viewmodel.getEditItemTask(
                    binding.titleInput.text.toString(),
                    binding.descriptionInput.text.toString()

                )
            }

    }

    private fun launchAddMode() {

        binding.saveButton.setOnClickListener {
                viewmodel.getAddItemTask(
                    binding.titleInput.text.toString(),
                    binding.descriptionInput.text.toString()
                )
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


    private fun initViews() {
        viewmodel = ViewModelProvider(this)[ChangeViewModel::class.java]
    }


    interface EdingFragment {
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