package com.umang.reminderapp.data.models

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umang.reminderapp.singletons.TodoManager
import com.umang.reminderapp.data.classes.TodoItem
import java.time.Instant
import java.time.LocalDate
import java.util.Date

class TodoViewModel: ViewModel() {
    private var _todoList = MutableLiveData<SnapshotStateList<TodoItem>>()
    var todoList : LiveData<SnapshotStateList<TodoItem>> = _todoList

    init {
        getAllToDo()
    }

    fun getAllToDo(){
        _todoList.value = TodoManager.getAllToDo()
        Log.d("TodoManager", "getAllToDo: ${todoList.value.toString()}")
    }

    fun getToDoItem(id: Int): TodoItem?{
        return TodoManager.getToDoItem(id)
    }

    fun addTodoItem(
        title: String = " ToDo",
        description: String = "Hello World. THis is a description",
        dueDate: String = LocalDate.now().toString(),
        tags: List<String> = listOf("tag1","tag2")
    ){

        TodoManager.addTodoItem(
                title = title,
                description = description,
                dueDate = dueDate,
                tags = tags
        )
        getAllToDo()
        Log.d("TodoManager", "addTodoItem: ${todoList.value.toString()}")

    }

    fun deleteTodoItem(id: Int){

        TodoManager.deleteTodoItem(id = id)
        getAllToDo()
        Log.d("TodoManager", "deleteTodoItem: ${todoList.value.toString()}")

    }

    fun updateTodoItem(
        updatedTodoTitle : String,
        updatedTodoDescription : String,
        updatedTodoDueDate : String,
        updatedTodoTags : List<String>,
        toUpdateTodoItemID: Int
    ){
        TodoManager.updateTodoItem(
                updatedTodoTitle,
                updatedTodoDescription,
                updatedTodoDueDate,
                updatedTodoTags,
                toUpdateTodoItemID
        )
        getAllToDo()
        Log.d("TodoManager", "updateTodoItem: ${todoList.value.toString()}")
    }

//    fun createDummyTodo(){
//        TodoManager.createDummyTodo()
//        getAllToDo()
//    }

}
