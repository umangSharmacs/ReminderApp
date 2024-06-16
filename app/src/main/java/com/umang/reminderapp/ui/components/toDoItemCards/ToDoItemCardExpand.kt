package com.umang.reminderapp.ui.components.toDoItemCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.data.classes.TodoItem
import com.umang.reminderapp.ui.theme.ReminderAppTheme

@Composable
fun ToDoItemCardExpand(
    modifier: Modifier = Modifier,
    item: TodoItem,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
        ),
        shape = MaterialTheme.shapes.large
    ){
        // DESCRIPTION
        Row(modifier=Modifier.padding(5.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .padding(end = 15.dp, bottom = 15.dp)
                    .weight(6f),
                text = item.description
            )
        }

        // EDIT AND DELETE
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,

            ){
            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.weight(6f)) {
                // EDIT
                ElevatedAssistChip(
                    modifier = Modifier.padding(5.dp),
                    onClick = onEdit ,
                    leadingIcon = {Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")},
                    label = { Text(text = "Edit") }
                )
                // DELETE
                ElevatedAssistChip(
                    modifier = Modifier.padding(5.dp),
                    onClick = onDelete ,
                    leadingIcon = {Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")},
                    label = { Text(text = "Delete") }
                )
            }
        }

    }


}


@Preview
@Composable
fun ToDoItemCardClickedPreview() {
    val item = TodoItem()
    item.title = "TitleTitleTitleTitleTitleTitleTitleTitle"
    item.tags = listOf("tag1", "tag2")
    item.description = "Hello World. THis is a test description to see the result of the text overflow"

    ReminderAppTheme {
        ToDoItemCardExpand(item = item, onClick = { }, onEdit = {}, onDelete = {})
    }
}