package com.umang.reminderapp.screens.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.umang.reminderapp.data.models.AuthViewModel
import com.umang.reminderapp.data.models.TodoViewModel
import com.umang.reminderapp.ui.components.BottomBarScaffold
import com.umang.reminderapp.ui.components.FloatingActionButton
import com.umang.reminderapp.ui.components.TodoList
import com.umang.reminderapp.ui.components.TopAppBarScaffold

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    todoViewModel: TodoViewModel,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    Scaffold(
        topBar = @Composable {
            TopAppBarScaffold()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("AdderScreen") })
        },
        bottomBar = {
            BottomBarScaffold(
                navHost = navController
            )
        }
    ) {
        innerPadding ->
        TodoList(
            viewModel = todoViewModel,
            modifier = Modifier,
            navController,
            paddingValues = innerPadding,
            authViewModel = authViewModel
        )
    }

}