package com.umang.reminderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.umang.reminderapp.data.models.TodoViewModel
import com.umang.reminderapp.screens.main.AdderScreen
import com.umang.reminderapp.screens.main.EditorScreen
import com.umang.reminderapp.screens.main.TodoListPage
import com.umang.reminderapp.ui.components.FloatingActionButton
import com.umang.reminderapp.ui.components.TopAppBarScaffold
import com.umang.reminderapp.ui.theme.ReminderAppTheme

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
                        route = "EditScreen?title={title}&description={description}&id={id}",
                        arguments = listOf(
                            navArgument("title"){
                                type = NavType.StringType},
                            navArgument("description"){
                                type = NavType.StringType
                                nullable=true},
                            navArgument("id"){
                                type = NavType.IntType}
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
        topBar = @androidx.compose.runtime.Composable {
            TopAppBarScaffold()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("AdderScreen") })
        }
    ) {innerPadding ->
        TodoListPage(viewModel = todoViewModel, modifier = Modifier, navController, paddingValues = innerPadding)
    }

}
