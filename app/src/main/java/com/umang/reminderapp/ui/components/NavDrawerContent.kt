package com.umang.reminderapp.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umang.reminderapp.data.classes.NavigationItem
import com.umang.reminderapp.ui.theme.Shapes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavDrawerContent(
    navigationItems: List<NavigationItem>,
    currentIndex: Int,
    navController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    var selectedIndex by rememberSaveable {
        mutableStateOf(currentIndex)
    }

    ModalDrawerSheet(
        modifier = Modifier
            .width(300.dp),
        drawerShape = Shapes.extraLarge,
    ) {
        Text(
            text = "Memento",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge,
            color = if(!isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.padding(16.dp))
        navigationItems.forEachIndexed { index, item ->
            NavigationDrawerItem(
                label = { Text(item.title) },
                selected = index == selectedIndex,
                onClick = {
                    scope.launch { drawerState.close() }
                    navController.navigate(item.navRoute)
                    selectedIndex = index
                },
                icon = {
                    Icon(
                        painter = if (selectedIndex == index) painterResource(id = item.selectedIcon) else painterResource(
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