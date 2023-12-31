package com.example.taskmanager.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskmanager.data.TaskRepositoryImpl
import com.example.taskmanager.domain.Task
import com.example.taskmanager.domain.usecase.TaskAddItemUseCase
import com.example.taskmanager.domain.usecase.TaskEditItemUseCase
import com.example.taskmanager.domain.usecase.TaskItemUseCase
import kotlin.random.Random

class ChangeViewModel : ViewModel() {

    private val repository = TaskRepositoryImpl
    private val tasItemUseCase = TaskItemUseCase(repository)
    private val taskAddItemUseCase = TaskAddItemUseCase(repository)
    private val taskEditItemUseCase = TaskEditItemUseCase(repository)

    val errorTitleEditText = MutableLiveData<Boolean>()
    val getErrorTitleEditTex: LiveData<Boolean>
        get() = errorTitleEditText

    val errorDescriptionEditText = MutableLiveData<Boolean>()
    val getErrorDescriptionEditTex: LiveData<Boolean>
        get() = errorDescriptionEditText

    val closeScreen = MutableLiveData<Unit>()
    val getCloseScreen: LiveData<Unit>
        get() = closeScreen

    val modelTaskItem = MutableLiveData<Task>()
    val getModelTaskItem: LiveData<Task>
        get() = modelTaskItem


    fun validateInput(title: String, description: String): Boolean {
        var result = true
        if (title.isBlank()) {
            errorTitleEditText.value = true
            result = false
        }
        if (description.isBlank()) {
            errorDescriptionEditText.value = true
            result = false
        }

        return result
    }


    fun getAddItemTask(title: String, description: String) {
        val titleParse = parseTextInput(title)
        val descriptionParse = parseTextInput(description)
        val valid = validateInput(titleParse,descriptionParse)
        if (valid) {
           val taskItem = Task(titleParse,descriptionParse,true)
            taskAddItemUseCase.addTaskItem(taskItem)
            finishScreen()
        }
    }


   private fun parseTextInput(textInput:String):String{
        return textInput.trim() ?: ""
    }


    fun getEditItemTask( title: String, description: String) {
        val valid = validateInput(title, description)
        if (valid) {
            modelTaskItem.value?.let {
                val task = it.copy(title = title, description = description, enable = true)
                taskEditItemUseCase.editItemUseCase(task)
                finishScreen()
            }

        }
    }

    fun getTaskItem(id: Int) {
        val item = tasItemUseCase.getItemTask(id)
        modelTaskItem.value = item
        finishScreen()
    }

    fun resetErrorTitle() {
        errorTitleEditText.value = false
    }

    fun resetErrorDescription() {
        errorDescriptionEditText.value = false
    }

    fun finishScreen() {
        closeScreen.value = Unit
    }
}