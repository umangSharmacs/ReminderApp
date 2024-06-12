package com.umang.reminderapp.ui.components

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.umang.reminderapp.R
import com.umang.reminderapp.data.classes.BottomBarNavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarScaffold(modifier: Modifier = Modifier,
                      navHost: NavHostController) {

    val bottomBarNavigationItems = listOf(
        BottomBarNavigationItem.Home,
        BottomBarNavigationItem.AllReminders,
        BottomBarNavigationItem.Profile
    )

    val navStackBackEntry by navHost.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    NavigationBar {
        bottomBarNavigationItems.forEachIndexed { index,item ->
            var isSelected = currentDestination?.hierarchy?.any{
                it.route==item.navRoute
            }==true
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navHost.navigate(item.navRoute)
                    Log.d("BottomBarScaffold", "BottomBarScaffold: $item.navRoute")
                          },
                icon = {
                    BadgedBox(
                        badge = {
                            if(item.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            painter = if (isSelected) {
                                painterResource(id = item.selectedIcon)
                            } else painterResource(item.unselectedIcon),
                            contentDescription = item.title
                        )
                    }},
                    label = {
                    Text(text = item.title)
                },
                alwaysShowLabel = true
            )

    }

}
}

@Preview
@Composable
fun BottomBarScaffoldPreview() {
    BottomBarScaffold(
        modifier = Modifier,
        navHost = NavHostController(LocalContext.current)
    )
}