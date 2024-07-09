package com.umang.reminderapp.data.classes

import com.umang.reminderapp.R

sealed class NavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val hasNews: Boolean = false,
    val navRoute: String
){
    data object Home : NavigationItem(
        title = "Home",
        selectedIcon = R.drawable.home_filled,
        unselectedIcon = R.drawable.outline_home,
        navRoute = "Home"
    )

    data object AllReminders : NavigationItem(
        title = "Reminders",
        selectedIcon = R.drawable.reminder_icon ,
        unselectedIcon = R.drawable.reminder_icon ,
        navRoute = "All Reminders"
    )

    data object Subscriptions : NavigationItem(
        title = "Subscriptions",
        selectedIcon = R.drawable.subscription ,
        unselectedIcon = R.drawable.subscription,
        navRoute = "Subscriptions"
    )

    data object Medicines : NavigationItem(
        title = "Medicines",
        selectedIcon = R.drawable.medicine_icon ,
        unselectedIcon = R.drawable.medicine_icon ,
        navRoute = "MedicinesScreen"
    )

    data object Profile : NavigationItem(
        title = "Profile",
        selectedIcon = R.drawable.user,
        unselectedIcon = R.drawable.outlined_user,
        navRoute = "ProfileScreen"
    )

}
