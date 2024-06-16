package com.umang.reminderapp.ui.components.toDoItemCards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umang.reminderapp.data.classes.TodoItem
import com.umang.reminderapp.data.models.TodoViewModel
import com.umang.reminderapp.ui.theme.ReminderAppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.exp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ToDoItemCard(
    modifier: Modifier = Modifier,
    item: TodoItem,
    viewModel: TodoViewModel,
    navHostController: NavHostController,
    onClick: () -> Unit
) {

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a")

    var expandedState by remember { mutableStateOf(false) }
    var checkBoxState by remember { mutableStateOf(item.completed) }

    //Animation States
//    var backGroundColor = remember { animateColorAsState(targetValue = Color.White)}

    OutlinedCard(
        modifier = modifier
            ,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        shape = MaterialTheme.shapes.large,
        onClick = { expandedState = !expandedState }
    ) {
        Column(){
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
                    checked = checkBoxState,
                    onCheckedChange = {
                        checkBoxState = !checkBoxState
                        item.completed = checkBoxState
                        // TODO Update Item Completion
                    }
                )

                // Centre Column
                Column(
                    modifier = Modifier.weight(5f)
                ) {
                    Text(text = item.title, color = MaterialTheme.colorScheme.onSecondaryContainer)


                }

                Column(modifier = Modifier.weight(3f)) {
                    Text(
                        modifier = Modifier.padding(top=10.dp, start = 5.dp, end = 5.dp),
                        text = "Due By",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = TextStyle.Default.copy(
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,

                            )
                    )
                    Text(
                        text = LocalDateTime.parse(item.dueDate).format(dateFormatter),
                        style = TextStyle.Default.copy(
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                            textAlign = TextAlign.Center
                        )
                    )

                    Text(
                        modifier = Modifier.padding(top=10.dp,start = 5.dp, end = 5.dp),
                        text = "Priority",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = TextStyle.Default.copy(
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                        )
                    )

                    Text(
                        text = when(item.priority){
                            1-> "High"
                            2-> "Medium"
                            3->"Low"
                            else -> ""
                        },
                        style = TextStyle.Default.copy(
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                            textAlign = TextAlign.Center
                        )
                    )

                }



            }
            // Tags
            Row(){
                Spacer(modifier=Modifier.weight(1f))
                // Tags row
                LazyRow(
                    modifier = Modifier.weight(6f),
                    contentPadding = PaddingValues(
                        top = 10.dp,
                        end = 12.dp
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
            // Reminders
            Row(){
                Spacer(modifier=Modifier.weight(1f))
                // Tags row
                LazyRow(
                    modifier = Modifier.weight(6f),
                    contentPadding = PaddingValues(

                        end = 12.dp,
                        bottom = 16.dp
                    )
                ) {
                    items(item.reminders.size) { index ->
                        AssistChip(
                            modifier = Modifier.padding(start=2.dp,end=2.dp),
                            onClick = { },
                            label = {
                                Text(
                                    text = LocalDateTime.parse(item.reminders[index]).format(dateFormatter),
                                    color = MaterialTheme.colorScheme.onSecondaryContainer)
                            }
                        )
                    }
                }
            }
            AnimatedVisibility(expandedState){
                ToDoItemCardExpand(
                    item = item,
                    onClick = { },
                    onEdit = { navHostController
                        .navigate(route = "EditScreen?title=${item.title}&description=${item.description}&id=${item.id}") },
                    onDelete = {  viewModel.deleteTodoItem(item.id) }
                )
            }

        }
    }
}


//
//@Preview
//@Composable
//fun ToDoIemCardPreview() {
//    val item = TodoItem()
//    item.title = "TitleTitleTitleTitleTitleTitleTitleTitle"
//    item.tags = listOf("tag1", "tag2")
//
//    ReminderAppTheme {
//        ToDoItemCard(item = item, onClick = {})
//    }
//}