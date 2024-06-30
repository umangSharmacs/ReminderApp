package com.umang.reminderapp.screens.bottomBarScreens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.umang.reminderapp.data.models.AuthViewModel
import com.umang.reminderapp.ui.components.BottomBarScaffold
import com.umang.reminderapp.ui.components.CustomFloatingActionButton
import com.umang.reminderapp.ui.components.FloatingActionButton
import com.umang.reminderapp.ui.components.ProfilePage
import com.umang.reminderapp.ui.components.TopAppBarScaffold

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navHost: NavHostController,
    authViewModel: AuthViewModel
) {


    Scaffold(
        topBar = @Composable {
            TopAppBarScaffold(
                navigateBack = {navHost.navigate("Home")}
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
                onMedicineClick = { },
                onReminderClick = {navHost.navigate("AdderScreen")}
            )
        },
        bottomBar = {
            BottomBarScaffold(
                navHost = navHost
            )
        }

    ) {innerPadding ->
        ProfilePage(
            modifier = Modifier,
            paddingValues = innerPadding,
            navHost = navHost,
            authViewModel = authViewModel
        )
    }
}