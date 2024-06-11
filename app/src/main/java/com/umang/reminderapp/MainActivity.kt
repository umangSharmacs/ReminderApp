package com.umang.reminderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.FirebaseApp
import com.umang.reminderapp.data.models.AuthViewModel
import com.umang.reminderapp.data.models.TodoViewModel
import com.umang.reminderapp.screens.login.LogInScreen
import com.umang.reminderapp.screens.login.SignUpLanding
import com.umang.reminderapp.screens.login.SignUpScreen
import com.umang.reminderapp.screens.main.AdderScreen
import com.umang.reminderapp.screens.main.EditorScreen
import com.umang.reminderapp.screens.main.HomePage
import com.umang.reminderapp.ui.theme.ReminderAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var startDestination = "SignUpLanding"

        // Start todoViewModel
        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
//        todoViewModel.createDummyTodo()

        // Start AuthViewModel
//        FirebaseApp.initializeApp(this)
        val authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        if (authViewModel.user == null){
            startDestination = "SignUpLanding"
        } else {
            startDestination = "Home"

//            viewModelScope.launch { todoViewModel.getAllToDo() }
        }

        setContent {
            ReminderAppTheme {

                // Navigation
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = startDestination){

                    // Auth
                    composable(route="SignUpLanding"){
                        SignUpLanding(navController = navController)
                    }

                    composable(route="SignUpScreen"){
                        SignUpScreen(navController = navController, AuthViewModel = authViewModel)
                    }

                    composable(route="LoginScreen"){
                        LogInScreen(navController = navController, AuthViewModel = authViewModel)
                    }

                    // Main
                    composable(route = "Home"){
                        HomePage(todoViewModel = todoViewModel, navController = navController, authViewModel = authViewModel)
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


