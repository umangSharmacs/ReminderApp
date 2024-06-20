package com.umang.reminderapp.data.models

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umang.reminderapp.singletons.TodoManager
import com.umang.reminderapp.data.classes.TodoItem
import kotlinx.coroutines.launch
import java.time.LocalDate

class TodoViewModel: ViewModel() {
    private var _todoList = MutableLiveData<SnapshotStateList<TodoItem>>()
    var todoList : LiveData<SnapshotStateList<TodoItem>> = _todoList

    init {
        getAllToDo()
    }

    fun getAllToDo(){
        viewModelScope.launch {
            _todoList.value = TodoManager.getAllToDo()
        }
        Log.d("TodoViewModel", "getAllToDo: ${todoList.value.toString()}")
    }

    fun getToDoItem(id: Int): TodoItem?{
        return TodoManager.getToDoItem(id)
    }

    fun addTodoItem(
        title: String = " ToDo",
        dueDate: String = LocalDate.now().toString(),
        tags: List<String> = emptyList(),
        description: String = "Hello World. This is a description",
        completed: Boolean = false,
        completedOn: String = "1900-01-01",
        reminders: List<String> = emptyList(),
        priority: Int = 3
    ): TodoItem {

        val todoItem = TodoManager.addTodoItem(
            title = title,
            dueDate = dueDate,
            tags = tags,
            description = description,
            completed = completed,
            completedOn = completedOn,
            reminders = reminders,
            priority = priority
        )
        this.viewModelScope.launch { getAllToDo() }
        Log.d("TodoManager", "addTodoItem: ${todoList.value.toString()}")
        return todoItem
    }

    fun deleteTodoItem(id: Int){
        TodoManager.deleteTodoItem(id = id)
        this.viewModelScope.launch { getAllToDo() }
        Log.d("TodoManager", "deleteTodoItem: ${todoList.value.toString()}")

    }

    fun updateTodoItem(
        updatedTodoTitle : String,
        updatedTodoDescription : String,
        updatedTodoDueDate : String,
        updatedTodoTags : List<String>,
        updatedPriority : Int,
        updatedReminders : List<String>,
        toUpdateTodoItemID: Int
    ): TodoItem? {
        val updatedItem = TodoManager.updateTodoItem(
            updatedTodoTitle = updatedTodoTitle,
            updatedTodoDescription = updatedTodoDescription,
            updatedTodoDueDate = updatedTodoDueDate,
            updatedTodoTags = updatedTodoTags,
            updatedPriority = updatedPriority,
            updatedReminders = updatedReminders,
            toUpdateTodoItemID = toUpdateTodoItemID
        )
        this.viewModelScope.launch { getAllToDo() }
        Log.d("TodoManager", "updateTodoItem: ${todoList.value.toString()}")

        return updatedItem

    }

}
