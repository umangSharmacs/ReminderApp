package com.example.reminderapp

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.reminderapp.ui.theme.ReminderAppTheme

@Composable
fun TodoListPage(viewModel: TodoViewModel, modifier: Modifier = Modifier, navHost: NavHostController, paddingValues: PaddingValues) {

    val todoList by viewModel.todoList.observeAsState()

    todoList?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(top = paddingValues.calculateTopPadding())
        ) {
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

@Preview(widthDp = 360, heightDp = 640)
@Composable
private fun TodoListPreview() {
    ReminderAppTheme {
        TodoListPage(viewModel = TodoViewModel(), navHost = NavHostController(LocalContext.current), paddingValues = PaddingValues(0.dp))
    }

}