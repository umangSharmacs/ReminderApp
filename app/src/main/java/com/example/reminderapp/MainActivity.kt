package com.example.reminderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reminderapp.ui.theme.ReminderAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReminderAppTheme {
                ShowAllItems()

                }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun ShowAllItems(modifier: Modifier = Modifier) {
    val todoItems = CreateDummyTodo()
    ReminderAppTheme {
        LazyColumn(

        ) {
            items( todoItems) {todoItem ->
                Item(Modifier.padding(15.dp),todoItem)
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val todoItems = CreateDummyTodo()
    val item = todoItems[0]
    ShowAllItems()
//    ReminderAppTheme {
//        Item(Modifier, item)
//    }
}