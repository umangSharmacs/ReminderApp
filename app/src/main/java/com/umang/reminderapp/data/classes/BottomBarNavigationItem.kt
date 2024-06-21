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
        title = "All Reminders",
        selectedIcon = R.drawable.tag_filled,
        unselectedIcon = R.drawable.tag_filled,
        navRoute = "All Reminders"
    )

    data object Profile : BottomBarNavigationItem(
        title = "Profile",
        selectedIcon = R.drawable.user,
        unselectedIcon = R.drawable.outlined_user,
        navRoute = "ProfileScreen"
    )

}
