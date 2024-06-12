package com.umang.reminderapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.umang.reminderapp.data.classes.BottomBarNavigationItem
import com.umang.reminderapp.data.models.AuthViewModel
import com.umang.reminderapp.data.models.TodoViewModel
import com.umang.reminderapp.screens.Placeholder.ComingSoon
import com.umang.reminderapp.screens.main.HomePage


@Composable
fun BottomBarNavGraph(
    navController: NavHostController,
    todoViewModel: TodoViewModel,
    authViewModel: AuthViewModel
) {

    NavHost(
        navController = navController,
        startDestination = BottomBarNavigationItem.Home.navRoute
    ){
        // HomePage
        composable(route = BottomBarNavigationItem.Home.navRoute){
            HomePage(todoViewModel = todoViewModel, navController = navController, authViewModel = authViewModel)
        }
        // All Reminders
        composable(route = BottomBarNavigationItem.AllReminders.navRoute){
            ComingSoon(Modifier, navController)
        }

        // Profile
        composable(route = BottomBarNavigationItem.Profile.navRoute){
            ComingSoon(Modifier, navController)
        }

    }
    
}