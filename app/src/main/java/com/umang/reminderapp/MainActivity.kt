package com.umang.reminderapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
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
import com.umang.reminderapp.screens.login.SignUpScreen
import com.umang.reminderapp.screens.main.AdderScreen
import com.umang.reminderapp.screens.main.AllRemindersPage
import com.umang.reminderapp.screens.main.AllSubscriptionsPage
import com.umang.reminderapp.screens.main.HomePage
import com.umang.reminderapp.screens.main.SubscriptionAdder
import com.umang.reminderapp.ui.components.AlarmPage
import com.umang.reminderapp.ui.theme.ReminderAppTheme
import kotlinx.coroutines.launch
import java.time.LocalDateTime

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
            startDestination = "Auth"
        } else {
            startDestination = "Main"
        }

        setContent {
            ReminderAppTheme {

                // Navigation
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = startDestination){

                    // AUTH
                    navigation(
                        startDestination = "SignUpScreen",
                        route = "Auth"
                    ){

                        composable(route="SignUpScreen"){
                            SignUpScreen(navController = navController, AuthViewModel = authViewModel)
                        }

                        composable(route="LoginScreen"){
                            LogInScreen(navController = navController, AuthViewModel = authViewModel)
                        }
                    }

                    // MAIN
                    navigation(
                        startDestination = "Home",
                        route = "Main"
                    ){
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

                        // Reminders
                        composable(route = BottomBarNavigationItem.AllReminders.navRoute){
                            AllRemindersPage(
                                todoViewModel = todoViewModel,
                                navController = navController,
                                scheduler = scheduler,
                                authViewModel = authViewModel
                            )
                        }

                        // Subscriptions
                        composable(route = BottomBarNavigationItem.Subscriptions.navRoute){
                            AllSubscriptionsPage(Modifier, navController)
                        }

                        // Profile
                        composable(route = BottomBarNavigationItem.Profile.navRoute){
                            ProfileScreen(Modifier, navController, authViewModel)
//                        AlarmPage(scheduler = scheduler)
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
                                todoViewModel = todoViewModel,
                                tagViewModel = tagViewModel,
                                navController = navController,
                                scheduler = scheduler
                            )
                        }

                        // Edit Screen
                        composable(
                            route = "EditScreen?id={id}",
                            arguments = listOf(
                                navArgument("id"){
                                    type = NavType.IntType}
                            )
                        ){
                            val id = it.arguments?.getInt("id")
                            if (id != null) {
                                AdderScreen(
                                    todoViewModel = todoViewModel,
                                    tagViewModel = tagViewModel,
                                    navController = navController,
                                    scheduler = scheduler,
                                    optionalID = id,

                                    editMode = true
                                )
                            }
                        }

                        // Subscription Adder Screen
                        composable(route = "SubscriptionAdderScreen"){
                            SubscriptionAdder(
                                paddingValues = PaddingValues(0.dp),
                                tagViewModel = tagViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}


