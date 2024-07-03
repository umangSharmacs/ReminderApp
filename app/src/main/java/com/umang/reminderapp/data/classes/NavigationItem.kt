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
        selectedIcon = R.drawable.tag_filled,
        unselectedIcon = R.drawable.tag_filled,
        navRoute = "All Reminders"
    )

    data object Subscriptions : NavigationItem(
        title = "Subscriptions",
        selectedIcon = R.drawable.baseline_subscription_filled,
        unselectedIcon = R.drawable.outline_subsriptions,
        navRoute = "Subscriptions"
    )

    data object Medicines : NavigationItem(
        title = "Medicines",
        selectedIcon = R.drawable.filled_medication,
        unselectedIcon = R.drawable.filled_medication,
        navRoute = "MedicinesScreen"
    )

    data object Profile : NavigationItem(
        title = "Profile",
        selectedIcon = R.drawable.user,
        unselectedIcon = R.drawable.outlined_user,
        navRoute = "ProfileScreen"
    )

}
