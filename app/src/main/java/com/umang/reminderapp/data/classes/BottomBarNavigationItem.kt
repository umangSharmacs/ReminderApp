package com.umang.reminderapp.data.classes

import androidx.compose.ui.graphics.vector.ImageVector
import com.umang.reminderapp.R
import kotlinx.coroutines.selects.select

sealed class BottomBarNavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val hasNews: Boolean = false,
    val navRoute: String
){
    data object Home : BottomBarNavigationItem(
        title = "Home",
        selectedIcon = R.drawable.home_filled,
        unselectedIcon = R.drawable.outline_home,
        navRoute = "Home"
    )

    data object AllReminders : BottomBarNavigationItem(
        title = "Reminders",
        selectedIcon = R.drawable.tag_filled,
        unselectedIcon = R.drawable.tag_filled,
        navRoute = "All Reminders"
    )

    data object Subscriptions : BottomBarNavigationItem(
        title = "Subscriptions",
        selectedIcon = R.drawable.baseline_subscription_filled,
        unselectedIcon = R.drawable.outline_subsriptions,
        navRoute = "Subscriptions"
    )

    data object Medicines : BottomBarNavigationItem(
        title = "Medicines",
        selectedIcon = R.drawable.filled_medication,
        unselectedIcon = R.drawable.filled_medication,
        navRoute = "MedicinesScreen"
    )

    data object Profile : BottomBarNavigationItem(
        title = "Profile",
        selectedIcon = R.drawable.user,
        unselectedIcon = R.drawable.outlined_user,
        navRoute = "ProfileScreen"
    )

}
