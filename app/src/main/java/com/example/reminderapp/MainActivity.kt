package com.example.reminderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reminderapp.ui.theme.ReminderAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        todoViewModel.createDummyTodo()
        setContent {
            ReminderAppTheme {

                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(onClick = { todoViewModel.addTodoItem() }) {
                            Icon(Icons.Filled.Add, contentDescription = "Add")
                        }
                    }
                ) {innerPadding ->
                    TodoListPage(viewModel = todoViewModel, modifier = Modifier.padding(innerPadding))
//                    manager.ShowTodoItems(modifier = Modifier.padding(innerPadding))
                }

            }
        }
    }
}

