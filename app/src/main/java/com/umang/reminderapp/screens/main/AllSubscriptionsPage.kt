package com.umang.reminderapp.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umang.reminderapp.alarm.AndroidAlarmSchedulerImpl
import com.umang.reminderapp.data.classes.NavigationItem
import com.umang.reminderapp.data.models.SubscriptionViewModel
import com.umang.reminderapp.ui.components.BottomBarScaffold
import com.umang.reminderapp.ui.components.CustomFloatingActionButton
import com.umang.reminderapp.ui.components.NavDrawerContent
import com.umang.reminderapp.ui.components.TopAppBarScaffold
import com.umang.reminderapp.ui.components.subscription.SubscriptionCostCard
import com.umang.reminderapp.ui.components.subscription.SubscriptionList
import kotlinx.coroutines.launch

@Composable
fun AllSubscriptionsPage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    subscriptionViewModel: SubscriptionViewModel,
    scheduler: AndroidAlarmSchedulerImpl
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
        mutableIntStateOf(0)
    }

    ModalNavigationDrawer(
        drawerContent = { NavDrawerContent(navigationItems, selectedIndex, navController, scope, drawerState) },
        drawerState = drawerState
    ){
        Scaffold(
            topBar = @Composable {
                TopAppBarScaffold(
                    header = "Subscriptions",
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
//            FloatingActionButton(onClick = { navController.navigate("AdderScreen") })
                CustomFloatingActionButton(
                    expandable = true,
                    onFabClick = { /*TODO*/ },
                    fabIcon = Icons.Filled.Add,
                    onSubscriptionClick = { navController.navigate("SubscriptionAdderScreen") },
                    onTagClick = { },
                    onMedicineClick = { navController.navigate("MedicineAdderScreen") },
                    onReminderClick = {navController.navigate("AdderScreen")}
                )
            },
//            bottomBar = {
//                BottomBarScaffold(
//                    navHost = navController
//                )
//            }
        ) { innerPadding ->

            Column(modifier = modifier.padding(innerPadding)) {

                // Show cost

                SubscriptionCostCard(subscriptionViewModel = subscriptionViewModel)

                SubscriptionList(
                    modifier = Modifier,
                    paddingValues = PaddingValues(0.dp),
                    navHost = navController,
                    subscriptionViewModel = subscriptionViewModel,
                    scheduler = scheduler
                )
            }
        }
    }




}