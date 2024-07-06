package com.umang.reminderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.umang.reminderapp.alarm.AndroidAlarmSchedulerImpl
import com.umang.reminderapp.data.classes.NavigationItem
import com.umang.reminderapp.data.models.AuthViewModel
import com.umang.reminderapp.data.models.MedicineViewModel
import com.umang.reminderapp.data.models.SubscriptionViewModel
import com.umang.reminderapp.data.models.TagViewModel
import com.umang.reminderapp.data.models.TodoViewModel
import com.umang.reminderapp.screens.bottomBarScreens.ProfileScreen
import com.umang.reminderapp.screens.login.LogInScreen
import com.umang.reminderapp.screens.login.SignUpScreen
import com.umang.reminderapp.screens.main.AdderScreen
import com.umang.reminderapp.screens.main.AllRemindersPage
import com.umang.reminderapp.screens.main.AllSubscriptionsPage
import com.umang.reminderapp.screens.main.HomePage
import com.umang.reminderapp.screens.main.MedicineAdderPage
import com.umang.reminderapp.screens.main.MedicineScreen
import com.umang.reminderapp.screens.main.SubscriptionAdderScreen
import com.umang.reminderapp.ui.theme.ReminderAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var startDestination: String

        // Start todoViewModel
        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        // Start SubscriptionViewModel
        val subscriptionViewModel = ViewModelProvider(this)[SubscriptionViewModel::class.java]
        // Start MedicineViewModel
        val medicineViewModel = ViewModelProvider(this)[MedicineViewModel::class.java]

        // Start TagViewModel
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
                        composable(route = NavigationItem.Home.navRoute){
                            HomePage(
                                todoViewModel = todoViewModel,
                                tagViewModel = tagViewModel,
                                navController = navController,
                                scheduler = scheduler,
                                authViewModel = authViewModel
                            )
                        }

                        // Reminders
                        composable(route = NavigationItem.AllReminders.navRoute){
                            AllRemindersPage(
                                todoViewModel = todoViewModel,
                                navController = navController,
                                scheduler = scheduler,
                                authViewModel = authViewModel
                            )
                        }

                        // Subscriptions
                        composable(route = NavigationItem.Subscriptions.navRoute){
                            AllSubscriptionsPage(Modifier, navController, subscriptionViewModel, scheduler)
                        }

                        // Medicines
                        composable(route = NavigationItem.Medicines.navRoute){
                            MedicineScreen(Modifier,medicineViewModel,scheduler, navController)
                        }

                        // Profile
                        composable(route = NavigationItem.Profile.navRoute){
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
                            SubscriptionAdderScreen(
                                navController = navController,
                                subscriptionViewModel = subscriptionViewModel,
                                tagViewModel = tagViewModel,
                                scheduler = scheduler,
                                editMode = false
                            )
                        }

                        // Subscription Editor Screen
                        composable(
                            route = "SubscriptionEditorScreen?id={id}",
                            arguments = listOf(
                                navArgument("id"){
                                    type = NavType.IntType}
                            )
                        ){
                            val id = it.arguments?.getInt("id")
                            if(id!=null){
                                SubscriptionAdderScreen(
                                    navController = navController,
                                    subscriptionViewModel = subscriptionViewModel,
                                    tagViewModel = tagViewModel,
                                    scheduler = scheduler,
                                    optionalID = id,
                                    editMode = true
                                )
                            }
                        }


                        // Medicine Adder Screen
                        composable(route = "MedicineAdderScreen"){
                            MedicineAdderPage(
                                modifier = Modifier,
                                navController = navController,
                                medicineViewModel = medicineViewModel,
                                scheduler = scheduler,
                                editMode = false
                            )
                        }

                        // Medicine Editor Screen
                        composable(
                            route = "MedicineEditorScreen?id={id}",
                            arguments = listOf(
                                navArgument("id"){
                                    type = NavType.IntType}
                            )
                        ){
                            val id = it.arguments?.getInt("id")
                            if(id!=null){
                                MedicineAdderPage(
                                    modifier = Modifier,
                                    navController = navController,
                                    medicineViewModel = medicineViewModel,
                                    scheduler = scheduler,
                                    optionalID = id,
                                    editMode = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


