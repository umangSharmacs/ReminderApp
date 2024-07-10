package com.umang.reminderapp.ui.components.toDoItem

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umang.reminderapp.alarm.AndroidAlarmSchedulerImpl
import com.umang.reminderapp.ui.components.TodoCategoryHeader
import com.umang.reminderapp.data.classes.UICategoryClasses.TodoDataClass
import com.umang.reminderapp.data.models.AuthViewModel
import com.umang.reminderapp.data.models.TodoViewModel
import com.umang.reminderapp.ui.components.swiping.BehindMotionSwipe

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoList(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel,
    navHost: NavHostController,
    scheduler: AndroidAlarmSchedulerImpl,
    paddingValues: PaddingValues,
    authViewModel: AuthViewModel
) {

    LaunchedEffect(Unit) {
        viewModel.getAllToDo()
    }

    val todoList by viewModel.todoList.observeAsState()

    val groupedList by remember {
        derivedStateOf {
            todoList?.groupBy { it.completed }?.map{
                TodoDataClass(
                    name = if(it.key) "Completed" else "Ongoing",
                    items = it.value
                )
            }
        }
    }
    val sortedGroupedList by remember {
        derivedStateOf { groupedList?.sortedBy { it.name }?.reversed() }
    }

    Log.d("TodoList", todoList?.toList().toString())

    val user = authViewModel.user
    Log.d("User", user?.email.toString())



    Column(modifier = modifier
        .fillMaxWidth()
        .padding(
            top = paddingValues.calculateTopPadding() - 15.dp,
            bottom = paddingValues.calculateBottomPadding()
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        sortedGroupedList?.let{
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(start = 15.dp, end = 15.dp),
                contentPadding = PaddingValues(top=15.dp, bottom = 15.dp)
            ){

                sortedGroupedList!!.forEach { group ->


                    stickyHeader {
                        TodoCategoryHeader(
                            modifier = Modifier.animateItemPlacement().background(MaterialTheme.colorScheme.background),
                            text = group.name
                        )
                    }




                    itemsIndexed(
                        items = group.items,
                        key = {index, item -> item.id }
                    ){
                        index, todoItem ->
                        BehindMotionSwipe(
                            content = {
                                ToDoItemCard(
                                    modifier = Modifier
                                        .padding(top = 10.dp, bottom = 10.dp)
                                        .animateItemPlacement(
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                                stiffness = Spring.StiffnessLow
                                            )
                                        ),
                                    item = todoItem,
                                    viewModel = viewModel,
                                    navHostController = navHost,
                                    scheduler = scheduler,
                                    onClick = {}
                                )
                            },
                            onEdit = { navHost.navigate(route = "EditScreen?id=${todoItem.id}") },
                            onDelete = { viewModel.deleteTodoItem(todoItem.id) },
                        )
                    }
                }
            }
        }


//        todoList?.let {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentWidth(Alignment.CenterHorizontally)
//                    .padding(start = 15.dp, end = 15.dp),
//                contentPadding = PaddingValues(top=15.dp, bottom = 15.dp)
//            ) {
//                itemsIndexed(
//                    items = it,
//                    key = {index, item -> item.id }
//                ) { index:Int, todoItem: TodoItem ->
//
//                    BehindMotionSwipe(
//                        content = {
//                            ToDoItemCard(
//                                modifier = Modifier
//                                    .padding(top = 10.dp, bottom = 10.dp)
//                                    .animateItemPlacement(),
//                                item = todoItem,
//                                viewModel = viewModel,
//                                navHostController = navHost,
//                                scheduler = scheduler,
//                                onClick = {}
//                            )
//                        },
//                        onEdit = { navHost.navigate(route = "EditScreen?id=${todoItem.id}") },
//                        onDelete = { viewModel.deleteTodoItem(todoItem.id) },
//                    )
//
//
//                }
//            }
//        }

        if(todoList?.size==0){
            Text(text = "No items")
        }

    }
}

//@Preview(widthDp = 360, heightDp = 640)
//@Composable
//private fun TodoListPreview() {
//    ReminderAppTheme {
//        TodoListPage(
//            viewModel = TodoViewModel(),
//            navHost = NavHostController(LocalContext.current),
//            paddingValues = PaddingValues(0.dp),
//            authViewModel = AuthViewModel())
//    }
//
//}