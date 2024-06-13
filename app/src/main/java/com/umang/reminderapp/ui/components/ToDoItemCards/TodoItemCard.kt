package com.umang.reminderapp.ui.components.ToDoItemCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.data.classes.TodoItem
import com.umang.reminderapp.ui.theme.ReminderAppTheme


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ToDoItemCard(modifier: Modifier = Modifier, item: TodoItem,
                 onClick: () -> Unit) {

    OutlinedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        shape = MaterialTheme.shapes.large,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            //Checkbox
            Checkbox(
                modifier = Modifier.weight(1f),
                checked = item.completed,
                onCheckedChange = { item.completed != item.completed })

            // Centre Column
            Column(
                modifier = Modifier.weight(4f)
            ) {
                Text(text = item.title, color = MaterialTheme.colorScheme.onSecondaryContainer)


            }

            Column(modifier = Modifier.weight(2f)) {
                Text(
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                    text = "Due By",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(text = item.dueDate)
            }



        }

        Row(){
            Spacer(modifier=Modifier.weight(1f))
            // Tags row
            LazyRow(
                modifier = Modifier.weight(6f),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    end = 12.dp,
                    bottom = 16.dp
                )
            ) {
                items(item.tags.size) { index ->
                    AssistChip(
                        modifier = Modifier.padding(2.dp),
                        onClick = { },
                        label = { Text(text = item.tags[index],color = MaterialTheme.colorScheme.onSecondaryContainer) }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun ToDoIemCardPreview() {
    val item = TodoItem()
    item.title = "TitleTitleTitleTitleTitleTitleTitleTitle"
    item.tags = listOf("tag1", "tag2")

    ReminderAppTheme {
        ToDoItemCard(item = item, onClick = {})
    }
}