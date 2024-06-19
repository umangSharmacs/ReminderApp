package com.umang.reminderapp.ui.components.homePageComponents

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.data.classes.TodoItem
import com.umang.reminderapp.ui.theme.ReminderAppTheme

@Composable
fun TodayItemList(modifier: Modifier = Modifier, todoItemList: List<TodoItem>) {

    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(
            top = 10.dp,
            end = 12.dp
        )
    ) {
        items(todoItemList.size){ index->
            MinimalItemRectangle(item = todoItemList[index])
        }
    }
}

@Preview
@Composable
fun TodayItemListPreview(){

    val itemList = listOf(
        TodoItem(),TodoItem(),TodoItem(),TodoItem()
    )

    ReminderAppTheme {
        TodayItemList(todoItemList = itemList)
    }
}