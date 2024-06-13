package com.umang.reminderapp.screens.bottomBarScreens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.umang.reminderapp.data.models.AuthViewModel
import com.umang.reminderapp.ui.components.BottomBarScaffold
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
            TopAppBarScaffold()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navHost.navigate("AdderScreen") })
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