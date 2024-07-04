package com.umang.reminderapp.screens.bottomBarScreens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.umang.reminderapp.data.classes.NavigationItem
import com.umang.reminderapp.data.models.AuthViewModel
import com.umang.reminderapp.ui.components.BottomBarScaffold
import com.umang.reminderapp.ui.components.CustomFloatingActionButton
import com.umang.reminderapp.ui.components.NavDrawerContent
import com.umang.reminderapp.ui.components.ProfilePage
import com.umang.reminderapp.ui.components.TopAppBarScaffold
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navHost: NavHostController,
    authViewModel: AuthViewModel
) {

    // Nav Drawer Items
    val navigationItems = listOf(
        NavigationItem.Home,
        NavigationItem.AllReminders,
        NavigationItem.Subscriptions,
        NavigationItem.Medicines,
        NavigationItem.Profile
    )

    // Nav Drawer State
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(navigationItems.indexOf(navigationItems.find {it == NavigationItem.Profile}))
    }
    
    ModalNavigationDrawer(
        drawerContent = { NavDrawerContent(navigationItems, selectedIndex, navHost, scope, drawerState) },
        drawerState = drawerState
    ){
        Scaffold(
            topBar = @Composable {
                TopAppBarScaffold(
                    header = "Profile",
                    navigateIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            floatingActionButton = {
//            FloatingActionButton(onClick = { navHost.navigate("AdderScreen") })
                CustomFloatingActionButton(
                    expandable = true,
                    onFabClick = { /*TODO*/ },
                    fabIcon = Icons.Filled.Add,
                    onSubscriptionClick = { navHost.navigate("SubscriptionAdderScreen") },
                    onTagClick = { },
                    onMedicineClick = { navHost.navigate("MedicineAdderScreen") },
                    onReminderClick = {navHost.navigate("AdderScreen")}
                )
            },
//        bottomBar = {
//            BottomBarScaffold(
//                navHost = navHost
//            )
//        }

        ) {innerPadding ->
            ProfilePage(
                modifier = Modifier,
                paddingValues = innerPadding,
                navHost = navHost,
                authViewModel = authViewModel
            )
        }
    }


}