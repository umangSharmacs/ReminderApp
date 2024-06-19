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
import com.umang.reminderapp.alarm.AndroidAlarmSchedulerImpl
import com.umang.reminderapp.data.classes.BottomBarNavigationItem
import com.umang.reminderapp.data.models.AuthViewModel
import com.umang.reminderapp.data.models.TagViewModel
import com.umang.reminderapp.data.models.TodoViewModel
import com.umang.reminderapp.screens.Placeholder.ComingSoon
import com.umang.reminderapp.screens.bottomBarScreens.ProfileScreen
import com.umang.reminderapp.screens.login.LogInScreen
import com.umang.reminderapp.screens.login.SignUpLanding
import com.umang.reminderapp.screens.login.SignUpScreen
import com.umang.reminderapp.screens.main.AdderScreen
import com.umang.reminderapp.screens.main.AllRemindersPage
import com.umang.reminderapp.screens.main.EditorScreen
import com.umang.reminderapp.screens.main.HomePage
import com.umang.reminderapp.ui.components.AlarmPage
import com.umang.reminderapp.ui.theme.ReminderAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var startDestination = "SignUpLanding"

        // Start todoViewModel
        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        val tagViewModel = ViewModelProvider(this)[TagViewModel::class.java]

        // Start AuthViewModel
        val authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        // Start AlarmScheduler
        val scheduler = AndroidAlarmSchedulerImpl(this)

        if (authViewModel.user == null){
            startDestination = "SignUpScreen"
        } else {
            startDestination = "Home"
        }

        setContent {
            ReminderAppTheme {

                // Navigation
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = startDestination){


                    // HomePage
                    composable(route = BottomBarNavigationItem.Home.navRoute){
                        HomePage(
                            todoViewModel = todoViewModel,
                            tagViewModel = tagViewModel,
                            navController = navController,
                            scheduler = scheduler,
                            authViewModel = authViewModel
                        )
                    }

                    // All Reminders
                    composable(route = BottomBarNavigationItem.AllReminders.navRoute){
                        AllRemindersPage(
                            todoViewModel = todoViewModel,
                            navController = navController,
                            scheduler = scheduler,
                            authViewModel = authViewModel
                        )
                    }

                    // Profile
                    composable(route = BottomBarNavigationItem.Profile.navRoute){
                        ProfileScreen(Modifier, navController, authViewModel)
//                        AlarmPage(scheduler = scheduler)
                    }

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
                        HomePage(
                            todoViewModel = todoViewModel,
                            tagViewModel = tagViewModel,
                            navController = navController,
                            scheduler = scheduler,
                            authViewModel = authViewModel
                        )
                    }

                    composable(route = "AdderScreen"){
                        AdderScreen(
                            modifier = Modifier,
                            todoViewModel,
                            tagViewModel,
                            navController,
                            scheduler = scheduler
                        )
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


