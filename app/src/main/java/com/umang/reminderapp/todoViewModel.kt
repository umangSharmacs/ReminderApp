package com.umang.reminderapp

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umang.reminderapp.ui.theme.ReminderAppTheme
import java.time.Instant
import java.time.LocalDate
import java.util.Date

class TodoViewModel: ViewModel() {
    private var _todoList = MutableLiveData<SnapshotStateList<todo_item>>()
    var todoList : LiveData<SnapshotStateList<todo_item>> = _todoList

    init {
        getAllToDo()
    }

    fun getAllToDo(){
        _todoList.value = TodoManager.getAllToDo()
    }

    fun getToDoItem(id: Int): todo_item?{
        return TodoManager.getToDoItem(id)
    }

    fun addTodoItem(
        title: String = " ToDo",
        description: String = "Hello World. THis is a description",
        dueDate: LocalDate = LocalDate.now(),
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
        updatedTodoDueDate : LocalDate,
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

    fun createDummyTodo(){
        TodoManager.createDummyTodo()
        getAllToDo()
    }

}
