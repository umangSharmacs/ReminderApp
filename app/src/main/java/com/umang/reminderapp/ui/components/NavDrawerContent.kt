package com.umang.reminderapp.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umang.reminderapp.data.classes.NavigationItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavDrawerContent(
    navigationItems: List<NavigationItem>,
    selectedIndex: Int,
    navController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    var selectedIndex1 = selectedIndex
    ModalDrawerSheet {
        Spacer(modifier = Modifier.padding(16.dp))
        navigationItems.forEachIndexed { index, item ->
            NavigationDrawerItem(
                label = { Text(item.title) },
                selected = index == selectedIndex1,
                onClick = {
                    navController.navigate(item.navRoute)
                    selectedIndex1 = index
                    scope.launch { drawerState.close() }
                },
                icon = {
                    Icon(
                        painter = if (selectedIndex1 == index) painterResource(id = item.selectedIcon) else painterResource(
                            item.unselectedIcon
                        ),
                        contentDescription = item.title
                    )
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}