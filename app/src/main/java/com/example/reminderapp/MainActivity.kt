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
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.reminderapp.ui.theme.ReminderAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        todoViewModel.createDummyTodo()
        setContent {
            ReminderAppTheme {

                // Navigation
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "Home"){

                    composable(route = "Home"){
                        HomePage(todoViewModel = todoViewModel, navController = navController)
                    }

                    composable(route = "AdderScreen"){
                        AdderScreen(modifier = Modifier, todoViewModel, navController)
                    }

                    composable(
                        route = "EditScreen/{title}/{description}/{id}",
                        arguments = listOf(
                            navArgument("title"){type = NavType.StringType},
                            navArgument("description"){type = NavType.StringType},
                            navArgument("id"){type = NavType.IntType}
                        )
                    ){
                        val title = it.arguments?.getString("title").toString()
                        val description = it.arguments?.getString("description").toString()
                        val id = it.arguments?.getInt("id")?.toInt()
                        if (id != null) {
                            EditorScreen(
                                modifier = Modifier,
                                todoViewModel,
                                navController,
                                title = title,
                                description = description,
                                id = id)
                        }
                    }
                }



            }
        }
    }
}

@Composable
fun HomePage(modifier: Modifier = Modifier, todoViewModel: TodoViewModel, navController : NavHostController) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("AdderScreen") }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) {innerPadding ->
        TodoListPage(viewModel = todoViewModel, modifier = Modifier.padding(innerPadding), navController)
    }

}
