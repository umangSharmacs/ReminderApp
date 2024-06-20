package com.umang.reminderapp.screens.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.umang.reminderapp.alarm.AndroidAlarmSchedulerImpl
import com.umang.reminderapp.data.models.AuthViewModel
import com.umang.reminderapp.data.models.TodoViewModel
import com.umang.reminderapp.ui.components.BottomBarScaffold
import com.umang.reminderapp.ui.components.FloatingActionButton
import com.umang.reminderapp.ui.components.TodoList
import com.umang.reminderapp.ui.components.TopAppBarScaffold

@Composable
fun AllRemindersPage(
    modifier: Modifier = Modifier,
    todoViewModel: TodoViewModel,
    navController: NavHostController,
    scheduler: AndroidAlarmSchedulerImpl,
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
            navHost = navController,
            paddingValues = innerPadding,
            scheduler = scheduler,
            authViewModel = authViewModel
        )
    }

}