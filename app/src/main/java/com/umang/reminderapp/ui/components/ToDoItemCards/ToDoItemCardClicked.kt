package com.umang.reminderapp.ui.components.ToDoItemCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.Icon
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

@Composable
fun ToDoItemCardClicked(
    modifier: Modifier = Modifier,
    item: TodoItem,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    OutlinedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        shape = MaterialTheme.shapes.large,
        onClick = onClick
    ) {
        Column(){
            // TOP ROW
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                //Checkbox
                Checkbox(
                    modifier = Modifier.weight(1f),
                    checked = item.completed,
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        checkmarkColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onCheckedChange = { item.completed != item.completed }
                )

                // TITLE
                Text(
                    modifier = Modifier.weight(4f),
                    text = item.title,
//                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )

                // DUE DATE
                Column(modifier = Modifier.weight(2f)) {
                    Text(
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                        text = "Due By",
//                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(text = item.dueDate)
                }
            }

            // TAGS
            Row(){
                Spacer(modifier = Modifier.weight(1f))
                // Tags row
                LazyRow(
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        end = 12.dp,
                        bottom = 16.dp
                    ),
                    modifier = Modifier.weight(6f)
                ) {
                    items(item.tags.size) { index ->
                        AssistChip(
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                labelColor = MaterialTheme.colorScheme.onBackground
                            ),
                            modifier = Modifier.padding(2.dp),
                            onClick = { },
                            label = { Text(text = item.tags[index]) }
                        )
                    }
                }
            }

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
        ToDoItemCardClicked(item = item, onClick = { }, onEdit = {}, onDelete = {})
    }
}