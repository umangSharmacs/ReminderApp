package com.umang.reminderapp.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umang.reminderapp.alarm.AndroidAlarmSchedulerImpl
import com.umang.reminderapp.data.classes.TodoItem
import com.umang.reminderapp.data.models.AuthViewModel
import com.umang.reminderapp.data.models.TodoViewModel
import com.umang.reminderapp.ui.components.toDoItemCards.MinimalItemCard
import com.umang.reminderapp.ui.components.toDoItemCards.ToDoItemCard

@Composable
fun MinimalToDoList(
    viewModel: TodoViewModel,
    modifier: Modifier = Modifier,
    navHost: NavHostController,
    scheduler: AndroidAlarmSchedulerImpl,
    paddingValues: PaddingValues,
    authViewModel: AuthViewModel,
    items: List<TodoItem>
) {

    LaunchedEffect(Unit) {
        viewModel.getAllToDo()
    }

    val user = authViewModel.user
    Log.d("User", user?.email.toString())

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(top = paddingValues.calculateTopPadding(), bottom = paddingValues.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(start = 15.dp, end = 15.dp),
                contentPadding = PaddingValues(top = 15.dp, bottom = 15.dp)
            ) {
                itemsIndexed(it) { index: Int, todoItem: TodoItem ->

                    MinimalItemCard(
                        Modifier.padding(top = 10.dp, bottom = 10.dp),
                        item = todoItem,
                        viewModel = viewModel,
                        navHostController = navHost,
                        scheduler = scheduler,
                        onClick = {}
                    )
                }
            }
        }

    }
}

