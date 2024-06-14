package com.umang.reminderapp.ui.components.HomePageComponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.umang.reminderapp.data.classes.TodoItem
import com.umang.reminderapp.data.models.TodoViewModel
import com.umang.reminderapp.ui.components.ToDoItemCards.ToDoItemCard
import com.umang.reminderapp.ui.components.ToDoItemCards.ToDoItemCardClicked


@Composable
fun ListedToDoItem(
    modifier: Modifier = Modifier,
    item: TodoItem,
    viewModel: TodoViewModel,
    navHostController: NavHostController
) {

    var expandedState by remember {
        mutableStateOf(false)
    }

    AnimatedVisibility(
        visible = !expandedState,
        enter = expandVertically(expandFrom = Alignment.Top),
        exit = shrinkVertically(shrinkTowards = Alignment.Bottom)
        ) {
        ToDoItemCard(
            modifier = modifier,
            item = item,
            onClick = { expandedState = !expandedState },
        )
    }
    AnimatedVisibility(
        visible = expandedState,
        enter = expandVertically(expandFrom = Alignment.Top),
        exit = shrinkVertically(shrinkTowards = Alignment.Bottom)
    ) {
        ToDoItemCardClicked(
            modifier = modifier,
            item = item,
            onClick = { expandedState = !expandedState },
            onEdit = {
                navHostController
                    .navigate(route = "EditScreen?title=${item.title}&description=${item.description}&id=${item.id}")
            },
            onDelete = { viewModel.deleteTodoItem(item.id)}
        )

    }

//    if(!expandedState){
//        ToDoItemCard(
//            modifier = modifier,
//            item = item,
//            onClick = { expandedState = !expandedState },
//        )
//    } else {
//        ToDoItemCardClicked(
//            modifier = modifier,
//            item = item,
//            onClick = { expandedState = !expandedState },
//            onEdit = {
//                navHostController
//                    .navigate(route = "EditScreen?title=${item.title}&description=${item.description}&id=${item.id}")
//                     },
//            onDelete = { viewModel.deleteTodoItem(item.id)}
//        )
//    }

}