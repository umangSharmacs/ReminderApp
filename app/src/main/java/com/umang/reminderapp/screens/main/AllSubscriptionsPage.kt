package com.umang.reminderapp.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umang.reminderapp.data.classes.SubscriptionItem
import com.umang.reminderapp.data.models.SubscriptionViewModel
import com.umang.reminderapp.ui.components.BottomBarScaffold
import com.umang.reminderapp.ui.components.CustomFloatingActionButton
import com.umang.reminderapp.ui.components.FloatingActionButton
import com.umang.reminderapp.ui.components.TopAppBarScaffold
import com.umang.reminderapp.ui.components.subscription.SubscriptionCostCard
import com.umang.reminderapp.ui.components.subscription.SubscriptionList

@Composable
fun AllSubscriptionsPage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    subscriptionViewModel: SubscriptionViewModel
) {

    Scaffold(
        topBar = @Composable {
            TopAppBarScaffold(
                navigateBack = {navController.popBackStack()}
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
                onMedicineClick = { },
                onReminderClick = {navController.navigate("AdderScreen")}
            )
                               },
        bottomBar = {
            BottomBarScaffold(
                navHost = navController
            )
        }
    ) { innerPadding ->

        Column(modifier = modifier.padding(innerPadding)) {

            // Show cost

            SubscriptionCostCard(subscriptionViewModel = subscriptionViewModel)

            SubscriptionList(
                modifier = Modifier,
                paddingValues = PaddingValues(0.dp),
                navHost = navController,
                subscriptionViewModel = subscriptionViewModel
            )
        }
    }


}