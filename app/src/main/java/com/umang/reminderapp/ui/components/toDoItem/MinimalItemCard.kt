package com.umang.reminderapp.ui.components.toDoItem

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umang.reminderapp.alarm.AndroidAlarmSchedulerImpl
import com.umang.reminderapp.data.classes.TodoItem
import com.umang.reminderapp.data.models.TodoViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MinimalItemCard(
    modifier: Modifier,
    item: TodoItem,
    viewModel : TodoViewModel,
    navHostController: NavHostController,
    scheduler : AndroidAlarmSchedulerImpl,
    onClick : () -> Unit
) {

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yy, h:mma")

    var checkBoxState by remember { mutableStateOf(item.completed) }

    //Animation States
    val backgroundColor =  animateColorAsState(MaterialTheme.colorScheme.primaryContainer,
        label = "Container Color"
    )
    val contentColor = animateColorAsState( MaterialTheme.colorScheme.background,
        label = "Content Color"
    )


    OutlinedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor.value,
            contentColor = contentColor.value,
        ),
        shape = MaterialTheme.shapes.large,
        onClick = { navHostController.navigate(route = "EditScreen?id=${item.id}") }
    ) {
        Column() {
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
                    },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = MaterialTheme.colorScheme.onPrimary
                    )
                )

                // Centre Column
                Column(
                    modifier = Modifier.weight(5f)
                ) {
                    Text(text = item.title, color = MaterialTheme.colorScheme.onPrimary)

                }

                Column(modifier = Modifier.weight(3f)) {

                    Text(
                        text = LocalDateTime.parse(item.dueDate).format(dateFormatter),
                        style = TextStyle.Default.copy(
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                            textAlign = TextAlign.Center
                        ),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }
    }
}




