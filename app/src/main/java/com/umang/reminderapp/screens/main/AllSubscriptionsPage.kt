package com.umang.reminderapp.screens.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.umang.reminderapp.data.classes.SubscriptionItem
import com.umang.reminderapp.ui.components.BottomBarScaffold
import com.umang.reminderapp.ui.components.FloatingActionButton
import com.umang.reminderapp.ui.components.TopAppBarScaffold
import com.umang.reminderapp.ui.components.subscription.SubscriptionList

@Composable
fun AllSubscriptionsPage(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    Scaffold(
        topBar = @Composable {
            TopAppBarScaffold(
                navigateBack = {navController.popBackStack()}
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("AdderScreen") })
        },
        bottomBar = {
            BottomBarScaffold(
                navHost = navController
            )
        }
    ) { innerPadding ->

        val itemList = listOf(
            SubscriptionItem(),
            SubscriptionItem(),
            SubscriptionItem(),
            SubscriptionItem(),
            SubscriptionItem(),
        )

        SubscriptionList(
            modifier = Modifier,
            paddingValues = innerPadding,
            subscriptionItemList = itemList
        )
    }



}