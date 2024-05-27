package com.example.reminderapp

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.reminderapp.ui.theme.ReminderAppTheme

@Composable
fun TodoListPage(viewModel: TodoViewModel, modifier: Modifier = Modifier, navHost: NavHostController) {

    val todoList by viewModel.todoList.observeAsState()


    todoList?.let {
        LazyColumn {
            itemsIndexed(it) { index:Int, todoItem: todo_item ->
                Item(Modifier.padding(15.dp),
                    todoItem,
                    onDelete = {
                        viewModel.deleteTodoItem(todoItem.id)
                               },
                    onEdit = {
                        navHost.navigate(route = "EditScreen/${todoItem.title}/${todoItem.description}/${todoItem.id}")
                    })
            }
        }
    }

}