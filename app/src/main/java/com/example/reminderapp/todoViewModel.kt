package com.example.reminderapp

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reminderapp.ui.theme.ReminderAppTheme
import java.time.Instant
import java.time.LocalDate
import java.util.Date

class TodoViewModel: ViewModel() {
    private var _todoList = MutableLiveData<List<todo_item>>()
    var todoList : LiveData<List<todo_item>> = _todoList

    fun getAllToDo(){
        _todoList.value = TodoManager.getAllToDo()
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
        Log.d("TodoManager", "addTodoItem: ${_todoList.toString()}")

    }

    fun deleteTodoItem(id: Int){

        TodoManager.deleteTodoItem(id = id)
        getAllToDo()

    }

    fun createDummyTodo(){
        TodoManager.createDummyTodo()
        getAllToDo()
    }

}
