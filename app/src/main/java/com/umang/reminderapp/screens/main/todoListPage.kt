package com.umang .reminderapp.screens.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umang.reminderapp.data.classes.TodoItem
import com.umang.reminderapp.data.models.AuthViewModel
import com.umang.reminderapp.data.models.TodoViewModel
import com.umang.reminderapp.ui.components.Item
import com.umang.reminderapp.ui.theme.ReminderAppTheme
import kotlin.coroutines.coroutineContext

@Composable
fun TodoListPage(viewModel: TodoViewModel,
                 modifier: Modifier = Modifier,
                 navHost: NavHostController,
                 paddingValues: PaddingValues,
                 authViewModel: AuthViewModel ) {

    LaunchedEffect(Unit) {
        viewModel.getAllToDo()
    }
    val todoList by viewModel.todoList.observeAsState()

    Log.d("TodoList", todoList?.toList().toString())

    val user = authViewModel.user
    Log.d("User", user?.email.toString())

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(top = paddingValues.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (user!=null){
            Text(text = "User email - "+user.email.toString(), color = Color.Black)
        } else {
            Text(text = "Not Signed In", color = Color.Black)
        }
        todoList?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(top = paddingValues.calculateTopPadding())
            ) {
                itemsIndexed(it) { index:Int, todoItem: TodoItem ->
                    Item(Modifier.padding(15.dp),
                        todoItem,
                        onDelete = {
                            viewModel.deleteTodoItem(todoItem.id)
                        },
                        onEdit = {
                            navHost.navigate(route = "EditScreen?title=${todoItem.title}&description=${todoItem.description}&id=${todoItem.id}")
                        })
                }
            }
        }

        if(todoList?.size==0){
            Text(text = "No items")
        }

        Button(onClick = {
            authViewModel.logout()
            navHost.navigate(route = "SignUpScreen")
        }) {
            Text(text = "Sign Out")
        }

        if (user != null && user.isAnonymous) {

                Button(onClick = {
                    navHost.navigate(route = "SignUpScreen")
                }) {
                    Text(text = "Sign up ")
                }
            }
        }

}

//@Preview(widthDp = 360, heightDp = 640)
//@Composable
//private fun TodoListPreview() {
//    ReminderAppTheme {
//        TodoListPage(
//            viewModel = TodoViewModel(),
//            navHost = NavHostController(LocalContext.current),
//            paddingValues = PaddingValues(0.dp),
//            authViewModel = AuthViewModel())
//    }
//
//}